package lt.vu.feedback_system.businesslogic.spreadsheets.exports.excel;

import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.businesslogic.spreadsheets.exports.SpreadsheetExporter;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.questions.RadioQuestion;
import lt.vu.feedback_system.entities.questions.SliderQuestion;
import lt.vu.feedback_system.entities.questions.TextQuestion;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExcelExporter implements SpreadsheetExporter {

    private AnswerDAO answerDAO;

    private QuestionDAO questionDAO;

    protected ExcelExporter() {
    }

    @Inject
    public ExcelExporter(AnswerDAO answerDAO, QuestionDAO questionDAO) {
        this.answerDAO = answerDAO;
        this.questionDAO = questionDAO;
    }

    public StreamedContent exportSurvey(final Survey survey) throws IOException {
        final String fileName = String.format("%s.xlsx", survey.getTitle());
        final File workbookFile = File.createTempFile("tmp-survey", ".xlsx");
        final Workbook workbook = new XSSFWorkbook();
        final Sheet surveySheet = workbook.createSheet("Survey");
        final Sheet answerSheet = workbook.createSheet("Answer");
        final List<Question> questions = getQuestions(survey);
        final Map<Integer, List<Answer>> groupedAnswers = groupAnswersByAnsweredSurveyId(getAnswers(survey), survey);

        ExcelSurveySheetFiller.fillSurveySheet(surveySheet, questions);
        ExcelAnswerSheetFiller.fillAnswerSheet(answerSheet, groupedAnswers, questions);

        workbook.write(new FileOutputStream(workbookFile));

        return new DefaultStreamedContent(new FileInputStream(workbookFile), HelperValues.ContentTypes.Xlsx, fileName);
    }

    private List<Question> getQuestions(final Survey survey) {
        final List<Question> questions = new ArrayList<>();
        survey.getSections().forEach(s -> {
            final List<TextQuestion> text = questionDAO.getTextQuestions(s);
            final List<CheckboxQuestion> checkbox = questionDAO.getCheckboxQuestions(s);
            final List<RadioQuestion> radio = questionDAO.getRadioQuestions(s);
            final List<SliderQuestion> slider = questionDAO.getSliderQuestions(s);
            final List<Question> tmp = new ArrayList<>(text.size() + checkbox.size() + radio.size() + slider.size());
            tmp.addAll(text);
            tmp.addAll(checkbox);
            tmp.addAll(radio);
            tmp.addAll(slider);
            tmp.sort(Comparator.comparingInt(Question::getPosition));

            questions.addAll(tmp);
        });
        return questions;
    }

    private List<Answer> getAnswers(final Survey survey) {
        final List<Answer> answers = new ArrayList<>();
        survey.getSections().forEach(s -> {
            final List<TextAnswer> text = answerDAO.getTextAnswers(s);
            final List<CheckboxAnswer> checkbox = answerDAO.getCheckboxAnswers(s);
            final List<RadioAnswer> radio = answerDAO.getRadioAnswers(s);
            final List<SliderAnswer> slider = answerDAO.getSliderAnswers(s);
            final List<Answer> tmp = new ArrayList<>(text.size() + checkbox.size() + radio.size() + slider.size());
            tmp.addAll(text);
            tmp.addAll(checkbox);
            tmp.addAll(radio);
            tmp.addAll(slider);
            tmp.sort(Comparator.comparingInt(a -> a.getQuestion().getPosition()));

            answers.addAll(tmp);
        });

        return answers;
    }

    private Map<Integer, List<Answer>> groupAnswersByAnsweredSurveyId(final List<Answer> answers, final Survey survey) {
        final List<AnsweredSurvey> answeredSurveys = survey.getAnsweredSurveys();
        return answers.stream().collect(Collectors.groupingBy(a -> answeredSurveys.indexOf(a.getAnsweredSurvey()), Collectors.toList()));
    }

}
