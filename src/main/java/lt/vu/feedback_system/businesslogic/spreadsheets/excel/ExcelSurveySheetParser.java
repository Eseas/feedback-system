package lt.vu.feedback_system.businesslogic.spreadsheets.excel;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.businesslogic.spreadsheets.ParsingHelperValues;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import lt.vu.feedback_system.utils.abstractions.Result;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.*;
import java.util.stream.Collectors;


final class ExcelSurveySheetParser {

    private final static DataFormatter formatter = new DataFormatter();

    private ExcelSurveySheetParser() {}

    /**
     * @param surveySheetResult excel sheet named 'Survey' which contains questions
     * @return map of question's position and question pairs. Map is then used by answer parsing. Also, map is used to
     * validate that there are no questions with same numbers.
     */
    static Result<Map<Integer, ? extends Question>> parseQuestions(final Result<Sheet> surveySheetResult) {
        final Result<Map<Integer, ? extends Question>> parsedQuestions;
        if (surveySheetResult.isSuccess()) {
            final Sheet surveySheet = surveySheetResult.get();
            final List<Row> rows = ExcelSheetParserHelper.filterOutEmptyRows(Lists.newArrayList(surveySheet.rowIterator()));
            if (rows.size() >= 2) {
                if (surveyFirstRowIsValid(surveySheet)) {
                    List<Result<? extends Question>> parsed = rows.subList(1, rows.size()).stream()
                            .map(ExcelSurveySheetParser::parseQuestion).collect(Collectors.toList());
                    Optional<Result<? extends Question>> firstFailure = parsed.stream().filter(Result::isFailure).findFirst();
                    if (!firstFailure.isPresent()) {
                        Map<Integer, ? extends Question> positionQuestionMap = parsed.stream()
                                .collect(Collectors.toMap(r -> r.get().getPosition(), Result::get, (v1, v2) -> v1));
                        if (parsed.size() == positionQuestionMap.size()) parsedQuestions = Result.Success(positionQuestionMap);
                        else parsedQuestions = Result.Failure("There are duplicate question numbers");
                    }
                    else parsedQuestions = Result.Failure(firstFailure.get().getFailureMsg());
                } else parsedQuestions = Result.Failure(String.format(
                    "First four cells of the first row in the Survey sheet must be filled with the following values: %s, %s, %s and %s",
                    ParsingHelperValues.SurveyFirstRow.FirstColValue,
                    ParsingHelperValues.SurveyFirstRow.SecondColValue,
                    ParsingHelperValues.SurveyFirstRow.ThirdColValue,
                    ParsingHelperValues.SurveyFirstRow.FourthColValue
                ));
            } else parsedQuestions = Result.Failure("Spreadsheet has no questions");
        } else parsedQuestions = Result.Failure(surveySheetResult.getFailureMsg());
        return parsedQuestions;
    }

    private static Result<? extends Question> parseQuestion(final Row row) {
        final Result<? extends Question> result;
        final List<Cell> cells = Lists.newArrayList(row.cellIterator());
        if (cells.size() >= 4) {
            int qNumber = ParserWithDefaults.parseInt(formatter.formatCellValue(cells.get(0)), -1);
            if (qNumber > 0) {
                final String qTitle = formatter.formatCellValue(cells.get(1));
                final String qType = formatter.formatCellValue(cells.get(2));
                switch (qType.toUpperCase()) {
                    case ParsingHelperValues.QuestionTypes.Text:
                        final TextQuestion textQuestion = (TextQuestion) setCommonFields(
                                new TextQuestion(), qTitle, false, qNumber);
                        result = Result.Success(textQuestion);
                        break;
                    case ParsingHelperValues.QuestionTypes.Radio:
                        final RadioQuestion radioQuestion = (RadioQuestion) setCommonFields(
                                new RadioQuestion(), qTitle, false, qNumber);
                        List<RadioButton> radioButtons = parseOptionList(cells, 4).stream().map(o -> {
                            final RadioButton radioButton = new RadioButton();
                            radioButton.setTitle(o);
                            radioButton.setQuestion(radioQuestion);
                            return radioButton;
                        }).collect(Collectors.toList());
                        radioQuestion.setRadioButtons(radioButtons);
                        result = Result.Success(radioQuestion);
                        break;
                    case ParsingHelperValues.QuestionTypes.Checkbox:
                        final CheckboxQuestion checkboxQuestion = (CheckboxQuestion) setCommonFields(
                                new CheckboxQuestion(), qTitle, false, qNumber);
                        List<Checkbox> checkboxes = parseOptionList(cells, 4).stream().map(o -> {
                            final Checkbox checkbox = new Checkbox();
                            checkbox.setTitle(o);
                            checkbox.setQuestion(checkboxQuestion);
                            return checkbox;
                        }).collect(Collectors.toList());
                        checkboxQuestion.setCheckboxes(checkboxes);
                        result = Result.Success(checkboxQuestion);
                        break;
                    case ParsingHelperValues.QuestionTypes.Slider:
                        final SliderQuestion sliderQuestion = (SliderQuestion) setCommonFields(
                                new SliderQuestion(), qTitle, false, qNumber);
                        final List<Integer> optionList = parseOptionList(cells, 4).stream().map(o ->
                                ParserWithDefaults.parseInt(o, Integer.MIN_VALUE)).collect(Collectors.toList());
                        if (optionList.size() == 2) {
                            int lowerBound = optionList.get(0);
                            int upperBound = optionList.get(1);
                            if (lowerBound != Integer.MIN_VALUE && upperBound != Integer.MAX_VALUE) {
                                if (lowerBound < upperBound) {
                                    sliderQuestion.setLowerBound(lowerBound);
                                    sliderQuestion.setUpperBound(upperBound);
                                    result = Result.Success(sliderQuestion);
                                } else result = Result.Failure("lower bound has to be less than upper bound");
                            } else result = Result.Failure("upper and lower bound have to be integral values");
                        } else result = Result.Failure(String.format("Question of type '%s' must have two values: upper and lower bound", qType));
                        break;
                    default:
                        result = Result.Failure(String.format("Question type '%s' is not valid", qType));
                }
            } else result = Result.Failure(String.format("Question number in row '%s' has to be integral value", row.getRowNum()));
        } else result = Result.Failure("One or more rows are missing values");
        return result;
    }

    private static List<String> parseOptionList(List<Cell> cells, int optionsStartAt) {
        return cells.stream().skip(optionsStartAt - 1).map(formatter::formatCellValue).filter(v -> !v.equals("")).collect(Collectors.toList());
    }

    private static Question setCommonFields(Question q, String title, boolean required, int position) {
        q.setTitle(title);
        q.setRequired(required);
        q.setPosition(position);
        return q;
    }

    private static boolean surveyFirstRowIsValid(final Sheet surveySheet) {
        final int firstRowNum = surveySheet.getFirstRowNum();
        final boolean isValid;
        if (firstRowNum == 0) {
            Row firstRow = surveySheet.getRow(firstRowNum);
            List<Cell> cells = firstRow != null ? Lists.newArrayList(firstRow) : new ArrayList<>();
            List<String> cellValues = cells.stream().map(formatter::formatCellValue).collect(Collectors.toList());
            isValid = cellValues.indexOf(ParsingHelperValues.SurveyFirstRow.FirstColValue) == 0
                    && cellValues.indexOf(ParsingHelperValues.SurveyFirstRow.SecondColValue) == 1
                    && cellValues.indexOf(ParsingHelperValues.SurveyFirstRow.ThirdColValue) == 2
                    && cellValues.indexOf(ParsingHelperValues.SurveyFirstRow.FourthColValue) == 3;
        } else isValid = false;
        return isValid;
    }

}
