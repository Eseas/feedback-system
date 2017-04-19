package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.AnsweredSurveyDAO;
import lt.vu.feedback_system.dao.OptionValueAnswerDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.AnsweredSurvey;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Named
@ViewScoped
public class AnswerSurveyController implements Serializable {
    @Getter
    @Setter
    private Integer id;

    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private AnsweredSurveyDAO answeredSurveyDAO;
    @Inject
    private OptionValueAnswerDAO optionValueAnswerDAO;

    @Inject
    private AnswerDAO answerDAO;

    @Getter
    private AnsweredSurvey answeredSurvey = new AnsweredSurvey();

    private List<Answer> answers = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();


    @Getter
    private TextQuestion textQuestion = new TextQuestion();

    public void loadData() {
        answeredSurvey.setSurvey(surveyDAO.getSurveyById(id));

        for (TextQuestion q : answeredSurvey.getSurvey().getTextQuestions()) {
            TextAnswer a = new TextAnswer();
            a.setQuestion(q);
            answers.add(a);
        }
        for (SliderQuestion q : answeredSurvey.getSurvey().getSliderQuestions()) {
            SliderAnswer a = new SliderAnswer();
            a.setQuestion(q);
            answers.add(a);
        }
        for (OptionQuestion q : answeredSurvey.getSurvey().getOptionQuestions()) {
            OptionAnswer a = new OptionAnswer();
            a.setQuestion(q);
            answers.add(a);
        }
    }


    public List<Answer> getAnswers() {

        Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer lhs, Answer rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                return lhs.getQuestion().getPosition() > rhs.getQuestion().getPosition() ? 1 : (lhs.getQuestion().getPosition() < rhs.getQuestion().getPosition() ) ? -1 : 0;
            }
        });
        return answers;
    }

    public void addSliderAnswer(SliderQuestion q) {
        SliderAnswer a = new SliderAnswer();
        a.setQuestion(q);
        a.setAnsweredSurvey(answeredSurvey);

        answeredSurvey.getSliderAnswers().add(a);
    }

    @Transactional
    public String answer() {
        answeredSurveyDAO.create(answeredSurvey);
        for (TextAnswer a: answeredSurvey.getTextAnswers())
            answerDAO.create(a);
        for (SliderAnswer a: answeredSurvey.getSliderAnswers())
            answerDAO.create(a);
        for (OptionAnswer a: answeredSurvey.getOptionAnswers()) {
            answerDAO.create(a);
            for (OptionValueAnswer ova : a.getOptionValueAnswers())
                optionValueAnswerDAO.create(ova);
        }
        return "surveys?faces-redirect=true";
    }
}