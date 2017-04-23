package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.*;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.utils.Sorter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private List<Question> questions = new ArrayList<>();

    public void loadData() {
        survey = surveyDAO.getSurveyById(surveyId);

        for (TextQuestion q: survey.getTextQuestions())
            questions.add(q);
        for (SliderQuestion q: survey.getSliderQuestions())
            questions.add(q);
        for (RadioQuestion q: survey.getRadioQuestions())
            questions.add(q);
        for (CheckboxQuestion q: survey.getCheckboxQuestions())
            questions.add(q);
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