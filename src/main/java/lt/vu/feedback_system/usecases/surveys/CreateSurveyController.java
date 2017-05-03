package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.businesslogic.surveys.QuestionLogic;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.businesslogic.users.Session;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CreateSurveyController implements Serializable {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Boolean modify;

    @Inject
    private UserContext userContext;

    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private SectionDAO sectionDAO;

    @Inject
    private QuestionDAO questionDAO;
    @Inject
    private CheckboxDAO checkboxDAO;
    @Inject
    private RadioButtonDAO radioButtonDAO;

    @Getter
    private Survey survey = new Survey();

    private Integer position = 1;

    @Inject
    private SurveyLogic surveyLogic;
    @Inject
    private QuestionLogic questionLogic;

    @PostConstruct
    private void init() {
        survey.setConfidential(true);
    }

    public void loadData() {
        survey = surveyDAO.getSurveyById(id);
    }

    public void moveUp(Section section, Question question) {
        questionLogic.moveUp(section, question);
    }

    public void moveDown(Section section, Question question) {
        questionLogic.moveDown(section, question);
    }

    public void addSection() {
        surveyLogic.addSection(survey);
    }

    public void addTextQuestion(Section section) {
        questionLogic.addQuestion(section, new TextQuestion());
    }

    public void removeQuestion(Section section, Question question) {
        for (Question tempQ : section.getQuestions()) {
            if (tempQ.getPosition() > question.getPosition())
                tempQ.setPosition(tempQ.getPosition() - 1);
        }

        section.getQuestions().remove(question);
    }

    public void addSliderQuestion(Section section) {
        questionLogic.addQuestion(section, new SliderQuestion());
    }

    public void addCheckboxQuestion(Section section) {
        questionLogic.addQuestion(section, new CheckboxQuestion());
    }

    public void addCheckbox(CheckboxQuestion checkboxQuestion) {
        Checkbox checkbox = new Checkbox();
        checkboxQuestion.getCheckboxes().add(checkbox);
        checkbox.setQuestion(checkboxQuestion);
    }

    public void removeCheckbox(CheckboxQuestion checkboxQuestion, Checkbox checkbox) {
        checkboxQuestion.getCheckboxes().remove(checkbox);
    }

    /**
     * Radio...
     */
    public void addRadioQuestion(Section section) {
        questionLogic.addQuestion(section, new RadioQuestion());

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
        survey.setCreator(userContext.getUser());
        surveyLogic.create(survey);

//        survey.setCreator(session.getUser());
//
//        surveyDAO.create(survey);


//        for (Section section : survey.getSections())
//            sectionDAO.create(section);
//        for (TextQuestion q: survey.getTextQuestions())
//            questionDAO.create(q);
//        for (SliderQuestion q: survey.getSliderQuestions())
//            questionDAO.create(q);
//        for (RadioQuestion q: survey.getRadioQuestions()) {
//            questionDAO.create(q);
//            for (RadioButton rb : q.getRadioButtons())
//                radioButtonDAO.create(rb);
//        }
//        for (CheckboxQuestion q: survey.getCheckboxQuestions()) {
//            questionDAO.create(q);
//            for (Checkbox c : q.getCheckboxes())
//                checkboxDAO.create(c);
//        }
        return "surveys?faces-redirect=true";
    }

//    @Transactional
//    public String update() {
////        surveyDAO.update(survey);
////        for (TextQuestion q: survey.getTextQuestions())
////            questionDAO.update(q);
////        for (SliderQuestion q: survey.getSliderQuestions())
////            questionDAO.update(q);
////        for (RadioQuestion q: survey.getRadioQuestions()) {
////            questionDAO.update(q);
////            for (RadioButton rb : q.getRadioButtons())
////                radioButtonDAO.update(rb);
////        }
////        for (CheckboxQuestion q: survey.getCheckboxQuestions()) {
////            questionDAO.update(q);
////            for (Checkbox c : q.getCheckboxes())
////                checkboxDAO.update(c);
////        }
////        return "surveys?faces-redirect=true";
//    }
}