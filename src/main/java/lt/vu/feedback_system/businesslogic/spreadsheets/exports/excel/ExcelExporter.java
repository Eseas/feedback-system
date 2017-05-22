package lt.vu.feedback_system.businesslogic.spreadsheets.exports.excel;

import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.AnsweredSurveyDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("exporter")
@ApplicationScoped
public class ExcelExporter {

    private SurveyDAO surveyDAO;

    private AnsweredSurveyDAO answeredSurveyDAO;

    private AnswerDAO answerDAO;

    private QuestionDAO questionDAO;

    protected ExcelExporter() {}

    @Inject
    public ExcelExporter(SurveyDAO surveyDAO, AnsweredSurveyDAO answeredSurveyDAO, AnswerDAO answerDAO, QuestionDAO questionDAO) {
        this.surveyDAO = surveyDAO;
        this.answeredSurveyDAO = answeredSurveyDAO;
        this.answerDAO = answerDAO;
        this.questionDAO = questionDAO;
    }

    public File exportSurvey(Survey survey) throws IOException {
        final File workbookFile = File.createTempFile("tmp-survey", ".xlsx");
        final Workbook workbook = new XSSFWorkbook();
        final Sheet surveySheet = workbook.createSheet("Survey");
        final Sheet answerSheet = workbook.createSheet("Answer");
        final List<Question> questions = getQuestions(survey);
        final Map<Integer, List<Answer>> groupedAnswers = groupAnswersByAnsweredSurveyId(getAnswers(survey), survey);

        ExcelSurveySheetFiller.fillSurveySheet(surveySheet, questions);
        ExcelAnswerSheetFiller.fillAnswerSheet(answerSheet, groupedAnswers, questions);

        workbook.write(new FileOutputStream(workbookFile));

        return workbookFile;
    }

    public StreamedContent getFile() throws IOException {
        final Survey survey = surveyDAO.getSurveyByLink("c4ca4238a0");
        final String fileName = String.format("%s.xlsx", survey.getTitle());
        final File surveyFile = exportSurvey(survey);

        return new DefaultStreamedContent(new FileInputStream(surveyFile), HelperValues.ContentTypes.Xlsx, fileName);
    }

    private List<Question> getQuestions(final Survey survey) {
        final List<Question> questions = new ArrayList<>();
        survey.getSections().forEach(s -> {
            questions.addAll(questionDAO.getTextQuestions(s));
            questions.addAll(questionDAO.getRadioQuestions(s));
            questions.addAll(questionDAO.getCheckboxQuestions(s));
            questions.addAll(questionDAO.getSliderQuestions(s));
        });
        return questions;
    }

    private List<Answer> getAnswers(final Survey survey) {
        final List<Answer> answers = new ArrayList<>();
        survey.getSections().forEach(s -> {
            answers.addAll(answerDAO.getTextAnswers(s));
            answers.addAll(answerDAO.getCheckboxAnswers(s));
            answers.addAll(answerDAO.getRadioAnswers(s));
            answers.addAll(answerDAO.getSliderAnswers(s));
        });
        return answers;
    }

    private Map<Integer, List<Answer>> groupAnswersByAnsweredSurveyId(final List<Answer> answers, final Survey survey) {
        final List<AnsweredSurvey> answeredSurveys = survey.getAnsweredSurveys();
        return answers.stream().collect(Collectors.groupingBy(a -> answeredSurveys.indexOf(a.getAnsweredSurvey()), Collectors.toList()));
    }

}
