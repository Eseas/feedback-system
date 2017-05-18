package lt.vu.feedback_system.businesslogic.spreadsheets;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.SectionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExcelImporter implements SpreadsheetImporter {

    // https://www.sitepoint.com/web-foundations/mime-types-summary-list/
    private final String xlsType = "application/vnd.ms-excel";

    private final String xlsxType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // 10 MB
    private final long maxSize = 10000000;

    private final DataFormatter formatter = new DataFormatter();

    private SurveyDAO surveyDAO;

    private SectionDAO sectionDAO;

    private QuestionDAO questionDAO;

    private AnswerDAO answerDAO;

    protected ExcelImporter() {}

    @Inject
    public ExcelImporter(SurveyDAO surveyDAO, SectionDAO sectionDAO, QuestionDAO questionDAO, AnswerDAO answerDAO) {
        this.surveyDAO = surveyDAO;
        this.sectionDAO = sectionDAO;
        this.questionDAO = questionDAO;
        this.answerDAO = answerDAO;
    }

    // TODO: only throw SpreadsheetImportException (handle IOException internally)
    // TODO: test more
    // TODO: write to db
    public void importSurvey(final Survey survey, UploadedFile file) throws SpreadsheetImportException, IOException {
        final Result<Workbook> workbookResult = getWorkbook(file);
        final Result<Sheet> surveySheetResult = getSheet(workbookResult, "Survey");
        final Result<Sheet> answerSheetResult = getSheet(workbookResult, "Answer");

        final Section defaultSection = new Section();
        final List<Section> sections = survey.getSections();
        defaultSection.setPosition(sections.size() + 1);
        defaultSection.setTitle("First section");
        defaultSection.setDescription("");
        defaultSection.setSurvey(survey);
        sections.add(defaultSection);

        System.out.println(surveySheetResult);
        System.out.println(answerSheetResult);
        Result<List<? extends Question>> parsedQuestions = parseQuestions(surveySheetResult, defaultSection);
        if (parsedQuestions.isSuccess()) {
            List<? extends Question> questions = parsedQuestions.get();
            System.out.println(questions);
            questions.forEach(System.out::println);
        } else System.out.println(parsedQuestions.getErrorMsg());
    }

    private Result<Workbook> getWorkbook(final UploadedFile file) throws IOException {
        final Result<Workbook> result;
        final String type = file.getContentType();
        final long size = file.getSize();

        if (size == 0) {
            result = Result.failure("No file selected");
        } else if (size > maxSize) {
            result = Result.failure(String.format("Maximum allowed file size is %.3f MB", maxSize / 1000000.0));
        } else {
            try (InputStream is = file.getInputstream()) {
                switch (type) {
                    case xlsType:
                        result = Result.success(new HSSFWorkbook(is));
                        break;
                    case xlsxType:
                        result = Result.success(new XSSFWorkbook(is));
                        break;
                    default:
                        result = Result.failure("Only .xls and .xlsx spreadsheet file formats are supported");
                        break;
                }
            }
        }

        return result;
    }

    private Result<Sheet> getSheet(final Result<Workbook> workbookResult, String sheetName) {
        return workbookResult.flatMap(w -> {
            Sheet s = w.getSheet(sheetName);
            return s != null ? Result.success(s) : Result.failure(String.format("Sheet named '%s' is required", sheetName));
        });
    }

    private Result<List<? extends Question>> parseQuestions(final Result<Sheet> surveySheetResult, final Section section) {
        final Result<List<? extends Question>> parsedQuestions;
        if (surveySheetResult.isSuccess()) {
            final Sheet surveySheet = surveySheetResult.get();
            final List<Row> rows = Lists.newArrayList(surveySheet.rowIterator());
            if (rows.size() >= 2) {
                if (surveyFirstRowIsValid(surveySheet)) {
                    List<Result<? extends Question>> parsed = rows.subList(1, rows.size()).stream().map(r -> parseQuestion(r, section)).collect(Collectors.toList());
                    Optional<Result<? extends Question>> firstFailure = parsed.stream().filter(Result::isFailure).findFirst();
                    if (!firstFailure.isPresent())
                        parsedQuestions = Result.success(parsed.stream().map(Result::get).collect(Collectors.toList()));
                    else parsedQuestions = Result.failure(firstFailure.get().getErrorMsg());
                } else parsedQuestions = Result.failure(ExcelImporterHelper.SurveyFirstRow.FirstColValidationErrorMsg);
            } else parsedQuestions = Result.failure("Spreadsheet has no questions");
        } else parsedQuestions = Result.failure(surveySheetResult.getErrorMsg());
        return parsedQuestions;
    }

    private Result<? extends Question> parseQuestion(final Row row, final Section section) {
        final Result<? extends Question> result;
        final List<Cell> cells = Lists.newArrayList(row.cellIterator());
        if (cells.size() >= 4) {
            int qNumber = ParserWithDefaults.parseInt(formatter.formatCellValue(cells.get(0)), -1);
            if (qNumber > 0) {
                final String qTitle = formatter.formatCellValue(cells.get(1));
                final String qType = formatter.formatCellValue(cells.get(2));
                switch (qType.toUpperCase()) {
                    case ExcelImporterHelper.QuestionTypes.Text:
                        final TextQuestion textQuestion = (TextQuestion) setCommonFields(
                                new TextQuestion(), qTitle, false, qNumber, section);
                        result = Result.success(textQuestion);
                        break;
                    case ExcelImporterHelper.QuestionTypes.Radio:
                        final RadioQuestion radioQuestion = (RadioQuestion) setCommonFields(
                                new RadioQuestion(), qTitle, false, qNumber, section);
                        List<RadioButton> radioButtons = parseOptionList(cells, 4).stream().map(o -> {
                            final RadioButton radioButton = new RadioButton();
                            radioButton.setTitle(o);
                            radioButton.setQuestion(radioQuestion);
                            return radioButton;
                        }).collect(Collectors.toList());
                        radioQuestion.setRadioButtons(radioButtons);
                        result = Result.success(radioQuestion);
                        break;
                    case ExcelImporterHelper.QuestionTypes.Checkbox:
                        final CheckboxQuestion checkboxQuestion = (CheckboxQuestion) setCommonFields(
                                new CheckboxQuestion(), qTitle, false, qNumber, section);
                        List<Checkbox> checkboxes = parseOptionList(cells, 4).stream().map(o -> {
                            final Checkbox checkbox = new Checkbox();
                            checkbox.setTitle(o);
                            checkbox.setQuestion(checkboxQuestion);
                            return checkbox;
                        }).collect(Collectors.toList());
                        checkboxQuestion.setCheckboxes(checkboxes);
                        result = Result.success(checkboxQuestion);
                        break;
                    case ExcelImporterHelper.QuestionTypes.Slider:
                        final SliderQuestion sliderQuestion = (SliderQuestion) setCommonFields(
                                new SliderQuestion(), qTitle, false, qNumber, section);
                        final List<Integer> optionList = parseOptionList(cells, 4).stream().map(o ->
                                ParserWithDefaults.parseInt(o, Integer.MIN_VALUE)).collect(Collectors.toList());
                        if (optionList.size() == 2) {
                            int lowerBound = optionList.get(0);
                            int upperBound = optionList.get(1);
                            if (lowerBound != Integer.MIN_VALUE && upperBound != Integer.MAX_VALUE) {
                                if (lowerBound < upperBound) {
                                    sliderQuestion.setLowerBound(lowerBound);
                                    sliderQuestion.setUpperBound(upperBound);
                                    result = Result.success(sliderQuestion);
                                } else result = Result.failure("lower bound has to be less than upper bound");
                            } else result = Result.failure("upper and lower bound have to be integral values");
                        } else result = Result.failure(String.format("Question of type '%s' must have two values: upper and lower bound", qType));
                        break;
                    default:
                        result = Result.failure(String.format("Question type '%s' is not valid", qType));
                }
            } else result = Result.failure(String.format("Question number in row '%s' has to be integral value", row.getRowNum()));
        } else result = Result.failure(String.format("Row number '%s' is missing values", row.getRowNum()));
        return result;
    }

    private List<String> parseOptionList(List<Cell> cells, int optionsStartAt) {
        return cells.stream().skip(optionsStartAt - 1).map(formatter::formatCellValue).filter(v -> !v.equals("")).collect(Collectors.toList());
    }

    private Question setCommonFields(Question q, String title, boolean required, int position, Section section) {
        q.setTitle(title);
        q.setRequired(required);
        q.setPosition(position);
        q.setSection(section);
        q.setSurvey(section.getSurvey());
        section.getQuestions().add(q);
        return q;
    }

    private boolean surveyFirstRowIsValid(final Sheet surveySheet) {
        final int firstRowNum = surveySheet.getFirstRowNum();
        final boolean isValid;
        if (firstRowNum == 0) {
            Row firstRow = surveySheet.getRow(firstRowNum);
            List<Cell> cells = firstRow != null ? Lists.newArrayList(firstRow) : new ArrayList<>();
            List<String> cellValues = cells.stream().map(formatter::formatCellValue).collect(Collectors.toList());
            isValid = cellValues.indexOf(ExcelImporterHelper.SurveyFirstRow.FirstColValue) == 0
                    && cellValues.indexOf(ExcelImporterHelper.SurveyFirstRow.SecondColValue) == 1
                    && cellValues.indexOf(ExcelImporterHelper.SurveyFirstRow.ThirdColValue) == 2
                    && cellValues.indexOf(ExcelImporterHelper.SurveyFirstRow.FourthColValue) == 3;
        } else isValid = false;
        return isValid;
    }

}

