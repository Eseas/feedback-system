package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lt.vu.feedback_system.businesslogic.users.Session;
import lt.vu.feedback_system.dao.OptionValueDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.questions.OptionValue;
import lt.vu.feedback_system.entities.questions.OptionQuestion;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.questions.SliderQuestion;
import lt.vu.feedback_system.entities.questions.TextQuestion;

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
    private OptionValueDAO optionValueDAO;

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
        questions.addAll(survey.getOptionQuestions());

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
            case "OptionQuestion":
                survey.getOptionQuestions().remove(q);
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
     * Option...
     */
    public void addOptionQuestion() {
        OptionQuestion q = new OptionQuestion();
        q.setRequired(false);
        q.setMultiple(false);

        q.setPosition(position++);
        q.setSurvey(survey);

        survey.getOptionQuestions().add(q);
    }

    public void addOptionValue(OptionQuestion optionQuestion) {
        OptionValue optionValue = new OptionValue();
        optionQuestion.getOptionValues().add(optionValue);
        optionValue.setQuestion(optionQuestion);
    }

    public void removeOptionValue(OptionQuestion optionQuestion, OptionValue optionValue) {
        optionQuestion.getOptionValues().remove(optionValue);
    }

    @Transactional
    public String create() {
        survey.setCreator(session.getUser());
        surveyDAO.create(survey);
        for (TextQuestion q: survey.getTextQuestions())
            questionDAO.create(q);
        for (SliderQuestion q: survey.getSliderQuestions())
            questionDAO.create(q);
        for (OptionQuestion q: survey.getOptionQuestions()) {
            questionDAO.create(q);
            for (OptionValue ov : q.getOptionValues())
                optionValueDAO.create(ov);
        }



        return "surveys?faces-redirect=true";
    }
}