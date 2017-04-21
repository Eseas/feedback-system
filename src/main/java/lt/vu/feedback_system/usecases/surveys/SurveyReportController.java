package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.*;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entity_utils.AnswerUtil;

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
    @Getter
    private List<AnsweredSurvey> answeredSurveys;

    private List<Question> questions = new ArrayList<>();

    private List<Answer> answers = new ArrayList<>();

    public void loadData() {
        survey = surveyDAO.getSurveyById(surveyId);

//        answeredSurveys = answeredSurveyDAO.getAnsweredSurveysBySurveyId(surveyId);
//        for(AnsweredSurvey answeredSurvey: answeredSurveys) {
//            for (TextAnswer a: answeredSurvey.getTextAnswers())
//                answers.add(a);
//            for (SliderAnswer a: answeredSurvey.getSliderAnswers())
//                answers.add(a);
//            for (RadioAnswer a: answeredSurvey.getRadioAnswers())
//                answers.add(a);
//            for (CheckboxAnswer a: answeredSurvey.getCheckboxAnswers())
//                answers.add(a);
//        }


        for (TextQuestion q: survey.getTextQuestions())
            questions.add(q);
        for (SliderQuestion q: survey.getSliderQuestions())
            questions.add(q);
        for (RadioQuestion q: survey.getRadioQuestions())
            questions.add(q);
        for (CheckboxQuestion q: survey.getCheckboxQuestions())
            questions.add(q);

    }

    public List<AnsweredSurvey> getAllAnsweredSurveys() {return answeredSurveyDAO.getAllAnsweredSurveys();}

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

    public List<Question> getQuestions() {

        Collections.sort(questions, new Comparator<Question>() {
            @Override
            public int compare(Question lhs, Question rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                return lhs.getPosition() > rhs.getPosition() ? 1 : (lhs.getPosition() < rhs.getPosition() ) ? -1 : 0;
            }
        });
        return questions;
    }

//    public List<Answer> getQuestionAnswers(Question q) {
////        return answerDAO.getAllTextAnswersByQuestionId(question.getId());
//        switch (q.getType()) {
//            case "TextQuestion":
//                return answerDAO.getAllTextAnswersByQuestionId(q.getId());
//            case "SliderQuestion":
//                survey.getSliderQuestions().remove(q);
//            case "CheckboxQuestion":
//                survey.getCheckboxQuestions().remove(q);
//            case "RadioQuestion":
//                survey.getRadioQuestions().remove(q);
//        }
//
//        List<Answer> answers = new ArrayList<>();
//
//        answers.addAll(answerDAO.getAllTextAnswersByQuestionId(question.getId()));
//        answers.addAll(answerDAO.getAllTextAnswersByQuestionId(question.getId()));
//        answers.addAll(answerDAO.getAllTextAnswersByQuestionId(question.getId()));
//        answers.addAll(answerDAO.getAllTextAnswersByQuestionId(question.getId()));
//
//        return AnswerUtil.sort(answers);
//    }

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