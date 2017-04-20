package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lt.vu.feedback_system.businesslogic.users.Session;
import lt.vu.feedback_system.dao.CheckboxDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.RadioButtonDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class CreateSurveyController implements Serializable {
    @Inject
    private Session session;

    @Inject
    private SurveyDAO surveyDAO;

    @Inject
    private QuestionDAO questionDAO;
    @Inject
    private CheckboxDAO checkboxDAO;
    @Inject
    private RadioButtonDAO radioButtonDAO;

    @Getter
    private Survey survey = new Survey();

    private Integer position = 1;

    @PostConstruct
    private void init() {
        survey.setConfidential(true);
    }

    public void moveUp(Question q) {
        List<Question> questions = getQuestions();

        if (q.getPosition() == 1)
            return;

        for (Question tempQ : questions) {
            if (tempQ.getPosition() + 1 == q.getPosition()) {
                q.setPosition(q.getPosition() - 1);
                tempQ.setPosition(tempQ.getPosition() + 1);
                break;
            }
        }
    }

    public void moveDown(Question q) {
        List<Question> questions = getQuestions();

        if (q.getPosition() == questions.size())
            return;

        for (Question tempQ : questions) {
            if (tempQ.getPosition() != q.getPosition())
                if (tempQ.getPosition() == (q.getPosition() + 1)) {
                    q.setPosition(q.getPosition() + 1);
                    tempQ.setPosition(tempQ.getPosition() - 1);
                    break;
                }
        }
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.addAll(survey.getTextQuestions());
        questions.addAll(survey.getSliderQuestions());
        questions.addAll(survey.getRadioQuestions());
        questions.addAll(survey.getCheckboxQuestions());

        return sort(questions);
    }

    public List<Question> sort(List<Question> questions) {
        Collections.sort(questions, (lhs, rhs) -> {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getPosition() > rhs.getPosition() ? 1 : (lhs.getPosition() < rhs.getPosition() ) ? -1 : 0;
        });
        return questions;
    }

    public List<Survey> getAllSurveys() {
        return surveyDAO.getAllSurveys();
    } // wrong usage

    /**
     * Text...
     */
    public void addTextQuestion() {
        TextQuestion q = new TextQuestion();
        q.setRequired(false);

        q.setPosition(position++);
        q.setSurvey(survey);

        survey.getTextQuestions().add(q);
    }

    public void removeQuestion(Question q) {
        for (Question tempQ : getQuestions()) {
            if (tempQ.getPosition() > q.getPosition())
                tempQ.setPosition(tempQ.getPosition() - 1);
        }

        switch (q.getType()) {
            case "TextQuestion":
                survey.getTextQuestions().remove(q);
            case "SliderQuestion":
                survey.getSliderQuestions().remove(q);
            case "CheckboxQuestion":
                survey.getCheckboxQuestions().remove(q);
            case "RadioQuestion":
                survey.getRadioQuestions().remove(q);
        }
    }

    /**
     * Slider...
     */
    public void addSliderQuestion() {
        SliderQuestion q = new SliderQuestion();
        q.setRequired(false);

        q.setPosition(position++);
        q.setSurvey(survey);

        survey.getSliderQuestions().add(q);
    }

    /**
     * Checkbox...
     */
    public void addCheckboxQuestion() {
        CheckboxQuestion q = new CheckboxQuestion();
        q.setRequired(false);

        q.setPosition(position++);
        q.setSurvey(survey);

        survey.getCheckboxQuestions().add(q);
    }

    /**
     * Radio...
     */
    public void addRadioQuestion() {
        RadioQuestion q = new RadioQuestion();
        q.setRequired(false);

        q.setPosition(position++);
        q.setSurvey(survey);

        survey.getRadioQuestions().add(q);
    }

    public void addCheckbox(CheckboxQuestion checkboxQuestion) {
        Checkbox checkbox = new Checkbox();
        checkboxQuestion.getCheckboxes().add(checkbox);
        checkbox.setQuestion(checkboxQuestion);
    }

    public void removeCheckbox(CheckboxQuestion checkboxQuestion, Checkbox checkbox) {
        checkboxQuestion.getCheckboxes().remove(checkbox);
    }

    public void addRadioButton(RadioQuestion radioQuestion) {
        RadioButton radioButton = new RadioButton();
        radioQuestion.getRadioButtons().add(radioButton);
        radioButton.setQuestion(radioQuestion);
    }

    public void removeRadioButton(RadioQuestion radioQuestion, RadioButton radioButton) {
        radioQuestion.getRadioButtons().remove(radioButton);
    }

    @Transactional
    public String create() {
        survey.setCreator(session.getUser());
        surveyDAO.create(survey);
        for (TextQuestion q: survey.getTextQuestions())
            questionDAO.create(q);
        for (SliderQuestion q: survey.getSliderQuestions())
            questionDAO.create(q);
        for (RadioQuestion q: survey.getRadioQuestions()) {
            questionDAO.create(q);
            for (RadioButton rb : q.getRadioButtons())
                radioButtonDAO.create(rb);
        }
        for (CheckboxQuestion q: survey.getCheckboxQuestions()) {
            questionDAO.create(q);
            for (Checkbox c : q.getCheckboxes())
                checkboxDAO.create(c);
        }
        return "surveys?faces-redirect=true";
    }
}