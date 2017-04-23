package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.utils.Sorter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class RequestSurveyController implements Serializable {
    @Getter
    @Setter
    private Integer id;

    @Inject
    private SurveyDAO surveyDAO;

    @Getter
    private Survey survey;

    private List<Question> questions = new ArrayList<>();

    @Getter
    private TextQuestion textQuestion = new TextQuestion();

    public void loadData() {
        survey = surveyDAO.getSurveyById(id);
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

}