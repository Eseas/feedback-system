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
    public double getAverage(Question q) {
        int sum = 0;
        int divider = 0;
        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());
        for ( SliderAnswer answer : answers) {
            sum += answer.getValue();
            divider++;
        }
        return sum/divider;
    }
    public List<Integer> getMedian(Question q) {
        List<Integer> median= new ArrayList<Integer>();
        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());

        if ((answers.size() % 2) == 0) {
            median.add(answers.get(answers.size() / 2 - 1).getValue());
            median.add(answers.get(answers.size() / 2).getValue());
        }
        else{
            median.add(answers.get(answers.size() / 2).getValue());
        }
        return median;
    }
    public int getMode(Question q){

        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());
            int mode = 0;
            int count = 0;

        for ( int i = 0; i< answers.size(); i++ ){
                int x = answers.get(i).getValue();
                int tempCount = 1;

            for ( int e = 0; e < answers.size(); e++ ){
                    int x2 = answers.get(e).getValue();

                    if( x == x2) {
                        tempCount++;
                    }
                    if( tempCount > count){
                        count = tempCount;
                        mode = x;
                    }
                }
            }
            return mode;
    }
}