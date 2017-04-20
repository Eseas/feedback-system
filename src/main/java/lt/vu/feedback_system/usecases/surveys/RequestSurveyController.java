package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.questions.SliderQuestion;
import lt.vu.feedback_system.entities.questions.TextQuestion;

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
public class RequestSurveyController implements Serializable {
//    For URL safety check here: (Now needs logged in user)
//    http://stackoverflow.com/questions/24744718/how-to-safely-init-a-viewscoped-bean-with-url-get-request-parameters
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
        for (CheckboxQuestion q: survey.getCheckboxQuestions())
            questions.add(q);
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

}