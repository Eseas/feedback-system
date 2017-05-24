package lt.vu.feedback_system.businesslogic.spreadsheets.imports.excel;

import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetException;
import lt.vu.feedback_system.businesslogic.spreadsheets.imports.SpreadsheetImporter;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.abstractions.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ExcelImporter implements SpreadsheetImporter {

    // 10 MB
    private final long maxSize = 10000000;

    private final DataFormatter formatter = new DataFormatter();

    private SurveyDAO surveyDAO;

    private SectionDAO sectionDAO;

    private QuestionDAO questionDAO;

    private AnsweredSurveyDAO answeredSurveyDAO;

    private AnswerDAO answerDAO;

    protected ExcelImporter() {}

    @Inject
    public ExcelImporter(SurveyDAO surveyDAO, SectionDAO sectionDAO, QuestionDAO questionDAO, AnsweredSurveyDAO answeredSurveyDAO, AnswerDAO answerDAO) {
        this.surveyDAO = surveyDAO;
        this.sectionDAO = sectionDAO;
        this.questionDAO = questionDAO;
        this.answeredSurveyDAO = answeredSurveyDAO;
        this.answerDAO = answerDAO;
    }

    @Transactional
    public void importSurvey(final Survey survey, UploadedFile file) throws SpreadsheetException, IOException {
        final Result<Workbook> workbookResult = getWorkbook(file);
        final Result<Sheet> surveySheetResult = getSheet(workbookResult, "Survey");
        final Result<Sheet> answerSheetResult = getSheet(workbookResult, "Answer");
        final Result<List<Question>> parsedQuestions = ExcelSurveySheetParser.parseQuestions(surveySheetResult);
        final Result<Map<Integer, List<Answer>>> parsedAnswers = ExcelAnswerSheetParser.parseAnswers(answerSheetResult, parsedQuestions);

        if (parsedAnswers.isSuccess()) createEntities(survey, parsedQuestions.get(), parsedAnswers.get());
        else throw new SpreadsheetException(parsedAnswers.getFailureMsg());
    }

    private void createEntities(final Survey survey, final List<Question> questions, final Map<Integer, List<Answer>> answers) {
        final Section defaultSection = new Section();
        defaultSection.setPosition(1);
        defaultSection.setTitle("First section");
        defaultSection.setDescription("");
        defaultSection.setSurvey(survey);

        questions.forEach(q -> {
            q.setSection(defaultSection);
            q.setSurvey(survey);
        });

        final List<AnsweredSurvey> answeredSurveys = new ArrayList<>(answers.keySet().size());
        answers.forEach((k, v) -> {
            final AnsweredSurvey answeredSurvey = new AnsweredSurvey();
            answeredSurvey.setSurvey(survey);
            v.forEach(answer -> answer.setAnsweredSurvey(answeredSurvey));
            answeredSurveys.add(answeredSurvey);
        });

        surveyDAO.create(survey);
        sectionDAO.create(defaultSection);
        questions.forEach(questionDAO::create);
        answeredSurveys.forEach(answeredSurveyDAO::create);
        answers.values().forEach(a -> a.forEach(answerDAO::create));
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
                    case HelperValues.ContentTypes.Xls:
                        result = Result.Success(new HSSFWorkbook(is));
                        break;
                    case HelperValues.ContentTypes.Xlsx:
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

