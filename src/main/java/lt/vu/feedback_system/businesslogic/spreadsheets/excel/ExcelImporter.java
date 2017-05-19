package lt.vu.feedback_system.businesslogic.spreadsheets.excel;

import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetException;
import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetImporter;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.SectionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.abstractions.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    // TODO: only throw SpreadsheetException (handle IOException internally)
    // TODO: test more
    // TODO: write to db
    public void importSurvey(final Survey survey, UploadedFile file) throws SpreadsheetException, IOException {
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
        Result<Map<Integer, ? extends Question>> parsedQuestions = ExcelSurveySheetParser.parseQuestions(surveySheetResult);
        parsedQuestions.map(questions -> {
            questions.values().forEach(q -> {
                q.setSection(defaultSection);
                q.setSurvey(survey);
                defaultSection.getQuestions().add(q);
            });
            return questions;
        });
        if (parsedQuestions.isSuccess()) {
            Map<Integer, ? extends Question> questions = parsedQuestions.get();
            System.out.println(questions);
            questions.forEach((k, v) -> System.out.println("Key: " + k + ", value: " + v));
        } else System.out.println(parsedQuestions.getFailureMsg());
    }

    private Result<Workbook> getWorkbook(final UploadedFile file) throws IOException {
        final Result<Workbook> result;
        final String type = file.getContentType();
        final long size = file.getSize();

        if (size == 0) {
            result = Result.Failure("No file selected");
        } else if (size > maxSize) {
            result = Result.Failure(String.format("Maximum allowed file size is %.3f MB", maxSize / 1000000.0));
        } else {
            try (InputStream is = file.getInputstream()) {
                switch (type) {
                    case xlsType:
                        result = Result.Success(new HSSFWorkbook(is));
                        break;
                    case xlsxType:
                        result = Result.Success(new XSSFWorkbook(is));
                        break;
                    default:
                        result = Result.Failure("Only .xls and .xlsx spreadsheet file formats are supported");
                        break;
                }
            }
        }

        return result;
    }

    private Result<Sheet> getSheet(final Result<Workbook> workbookResult, String sheetName) {
        return workbookResult.flatMap(w -> {
            Sheet s = w.getSheet(sheetName);
            return s != null ? Result.Success(s) : Result.Failure(String.format("Sheet named '%s' is required", sheetName));
        });
    }

}

