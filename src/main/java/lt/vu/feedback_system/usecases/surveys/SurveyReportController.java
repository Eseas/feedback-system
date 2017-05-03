package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.QuestionLogic;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.AnsweredSurveyDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.Sorter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class SurveyReportController implements Serializable {
    @Getter
    @Setter
    private Integer surveyId;

    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private AnsweredSurveyDAO answeredSurveyDAO;
    @Inject
    private AnswerDAO answerDAO;
    @Getter
    private Survey survey;

    @Inject
    private QuestionLogic questionLogic;

    @Getter
    private List<AnsweredSurvey> answeredSurveys;

    private List<Question> questions = new ArrayList<>();

    public void loadData() {
        survey = surveyDAO.getSurveyById(surveyId);
//        answeredSurveys = answeredSurveyDAO.getAnsweredSurveysBySurveyId(surveyId);

        for (Section section : survey.getSections()) {
            questionLogic.loadQuestionsToSection(section);

        }
//        for (AnsweredSurvey answeredSurvey : survey.getAnsweredSurveys()) {
//            for (Section section : answeredSurvey.getSurvey().getSections())
//                questionLogic.loadAnswersToSection(section);
//        }
//        survey = surveyDAO.getSurveyById(surveyId);

//        for (TextQuestion q: survey.getTextQuestions())
//            questions.add(q);
//        for (SliderQuestion q: survey.getSliderQuestions())
//            questions.add(q);
//        for (RadioQuestion q: survey.getRadioQuestions())
//            questions.add(q);
//        for (CheckboxQuestion q: survey.getCheckboxQuestions())
//            questions.add(q);
    }

    public List<Question> getQuestions() {
        return Sorter.sortQuestionsAscending(questions);
    }

    public List<TextAnswer> getQuestionTextAnswers(Question q) {
        return answerDAO.getAllTextAnswersByQuestionId(q.getId());
    }

    public List<SliderAnswer> getQuestionSliderAnswers(Question q) {
        return answerDAO.getAllSliderAnswersByQuestionId(q.getId());
    }

    public List<RadioAnswer> getQuestionRadioAnswers(Question q) {
        return answerDAO.getAllRadioAnswersByQuestionId(q.getId());
    }

    public List<CheckboxAnswer> getQuestionCheckboxAnswers(Question q) {
        return answerDAO.getAllCheckboxAnswersByQuestionId(q.getId());
    }

}