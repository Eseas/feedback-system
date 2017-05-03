package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.QuestionLogic;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.dao.SectionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.questions.*;
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
public class RequestSurveyController implements Serializable {
    @Getter
    @Setter
    private Integer id;

    @Inject
    private SurveyLogic surveyLogic;

    @Inject
    private SurveyDAO surveyDAO;

    @Inject
    private SectionDAO sectionDAO;

    @Getter
    private Survey survey;

    private List<Question> questions = new ArrayList<>();

    @Getter
    private TextQuestion textQuestion = new TextQuestion();

    public void loadData() {

        survey = surveyLogic.loadSurvey(id);
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

//    This method HAS TO BE checked twice
    public List<Question> getSectionQuestions(Section section) {
        List<Question> sectionQuestions = new ArrayList<>();

        for(Question question : questions) {
            if (question.getSection().getId() == section.getId()) {
                sectionQuestions.add(question);
            }
        }
        return sectionQuestions;
    }
}