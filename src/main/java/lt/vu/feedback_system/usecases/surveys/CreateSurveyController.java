package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.config.Configuration;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.usecases.users.NavigationBean;
import lt.vu.feedback_system.utils.Sorter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

@Named
@ViewScoped
public class CreateSurveyController implements Serializable {

    private Properties props;

    @Inject
    private Configuration config;

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Integer activeTabIndex;

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
    private Survey survey;

    @Inject
    private SurveyLogic surveyLogic;

    @Inject
    private NavigationBean navigationBean;

    @PostConstruct
    private void init() {
        this.props = config.getProps();
        survey = new Survey();
        addSection();
        activeTabIndex = 0;
    }

    public void loadData() {
        survey = surveyDAO.getSurveyById(id);
    }

    public void moveUp(Section section, Question question) {
        surveyLogic.moveUp(section, question);
    }

    public void moveDown(Section section, Question question) {
        surveyLogic.moveDown(section, question);
    }

    public void addSection() {
        activeTabIndex = surveyLogic.getNewSectionPosition(survey) - 1;
        surveyLogic.addSection(survey, props.getProperty("survey.defaultPageTitle"));
    }

    public void removeSection(Section section) {
        surveyLogic.removeSection(survey, section);
    }

    public void addTextQuestion(Section section) {
        surveyLogic.addQuestion(section, new TextQuestion());
    }

    public void removeQuestion(Section section, Question question) {
        for (Question tempQ : section.getQuestions()) {
            if (tempQ.getPosition() > question.getPosition())
                tempQ.setPosition(tempQ.getPosition() - 1);
        }

        section.getQuestions().remove(question);
    }

    public void addSliderQuestion(Section section) {
        surveyLogic.addQuestion(section, new SliderQuestion());
    }

    public void addCheckboxQuestion(Section section) {
        surveyLogic.addQuestion(section, new CheckboxQuestion());
    }

    public void addCheckbox(CheckboxQuestion checkboxQuestion) {
        Checkbox checkbox = new Checkbox();
        checkboxQuestion.getCheckboxes().add(checkbox);
        checkbox.setQuestion(checkboxQuestion);
    }

    public void removeCheckbox(CheckboxQuestion checkboxQuestion, Checkbox checkbox) {
        checkboxQuestion.getCheckboxes().remove(checkbox);
    }

    public void addRadioQuestion(Section section) {
        surveyLogic.addQuestion(section, new RadioQuestion());

    }

    public void addRadioButton(RadioQuestion radioQuestion) {
        RadioButton radioButton = new RadioButton();
        radioQuestion.getRadioButtons().add(radioButton);
        radioButton.setQuestion(radioQuestion);
    }

    public void removeRadioButton(RadioQuestion radioQuestion, RadioButton radioButton) {
        radioQuestion.getRadioButtons().remove(radioButton);
    }

    public List<Question> sortQuestionsAscending(List<Question> questions) {
        return Sorter.sortQuestionsAscending(questions);
    }

    @Transactional
    public String create() {
        survey.setCreator(userContext.getUser());
        surveyLogic.create(survey);

        return navigationBean.toMySurveys();
    }
}