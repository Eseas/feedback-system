package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
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

import javax.faces.context.FacesContext;
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
    private SurveyLogic surveyLogic;

    @Inject
    private SurveyContext surveyContext;

    @Getter
    private List<AnsweredSurvey> answeredSurveys;

    private List<Question> questions = new ArrayList<>();

    public void loadData() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("surveyContext", surveyContext);

        survey = surveyDAO.getSurveyById(surveyId);

        for (Section section : survey.getSections()) {
            surveyLogic.loadQuestionsToSection(section);

        }
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