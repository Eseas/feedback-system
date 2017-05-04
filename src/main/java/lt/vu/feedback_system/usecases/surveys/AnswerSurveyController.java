package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AnswerSurveyController implements Serializable {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Integer activeTabIndex;

    private Integer lastTabIndex;

//    @Setter
//    private Boolean submitActive;



    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private AnsweredSurveyDAO answeredSurveyDAO;
    @Inject
    private SelectedCheckboxDAO selectedCheckboxDAO;
    @Inject
    private SectionDAO sectionDAO;
    @Inject
    private AnswerDAO answerDAO;
    @Inject
    private SurveyLogic surveyLogic;

    @Getter
    private AnsweredSurvey answeredSurvey = new AnsweredSurvey();
//
//    private List<Question> questions = new ArrayList<>();
//
//
//    @Getter
//    private TextQuestion textQuestion = new TextQuestion();
//
    public void loadData() {

        answeredSurvey.setSurvey(surveyLogic.loadSurvey(id));
        for (Section section : answeredSurvey.getSurvey().getSections()) {
            surveyLogic.createEmptyAnswersForSection(section);
        }

        activeTabIndex = 0;
        lastTabIndex = answeredSurvey.getSurvey().getSections().size() - 1; // zero base

//        submitActive = false;
    }

//
//    public List<Section> getSections() {
//        return answeredSurvey.getSurvey().getSections();
//    }
//
//    public List<Answer> getAnswers() {
//        List<Answer> answers = new ArrayList<>();
//
//        answers.addAll(answeredSurvey.getTextAnswers());
//        answers.addAll(answeredSurvey.getSliderAnswers());
//        answers.addAll(answeredSurvey.getRadioAnswers());
//        answers.addAll(answeredSurvey.getCheckboxAnswers());
//
//        return Sorter.sortAnswersAscending(answers);
//    }
//
//    public List<Answer> getSectionAnswers(Section section) {
//        List<Answer> sectionAnswers = new ArrayList<>();
//
//        for(Answer answer : getAnswers()) {
//            if (answer.getQuestion().getSection().getId() == section.getId()) {
//                sectionAnswers.add(answer);
//            }
//        }
//        return sectionAnswers;
//    }
//
    @Transactional
    public String answer() {
        answeredSurveyDAO.create(answeredSurvey);
        for(Section section : answeredSurvey.getSurvey().getSections()) {
            for (Answer answer : section.getAnswers())
                answerDAO.create(answer);
        }
//        for (TextAnswer a: answeredSurvey.getTextAnswers())
//            answerDAO.create(a);
//        for (SliderAnswer a: answeredSurvey.getSliderAnswers())
//            answerDAO.create(a);
//        for (RadioAnswer a: answeredSurvey.getRadioAnswers()) {
//            answerDAO.create(a);
//        }
//        for (CheckboxAnswer a: answeredSurvey.getCheckboxAnswers()) {
//            answerDAO.create(a);
//            for (Checkbox checkbox : a.getTempSelectedCheckboxes()) {
//                SelectedCheckbox selectedCheckbox = new SelectedCheckbox();
//                selectedCheckbox.setCheckbox(checkbox);
//                selectedCheckbox.setCheckboxAnswer(a);
//                selectedCheckboxDAO.create(selectedCheckbox);
//            }
//
//        }
        return "surveys?faces-redirect=true";
    }


    public List<SelectedCheckbox> getAvailableSelectedCheckboxes(CheckboxAnswer checkboxAnswer) {

        if (checkboxAnswer.getAvailableSelectedCheckboxes() == null) {
            List<SelectedCheckbox> selectedCheckboxes = new ArrayList<>();

            for (Checkbox checkbox : checkboxAnswer.getQuestion().getCheckboxes()) {
                SelectedCheckbox selectedCheckbox = new SelectedCheckbox();

                selectedCheckbox.setCheckbox(checkbox);
                selectedCheckbox.setCheckboxAnswer(checkboxAnswer);

                selectedCheckboxes.add(selectedCheckbox);
            }
            checkboxAnswer.setAvailableSelectedCheckboxes(selectedCheckboxes);
        }

        return checkboxAnswer.getAvailableSelectedCheckboxes();
    }

    public void nextTab() {
        activeTabIndex += 1;
    }

    public void previousTab() {
//        TO DO IF...
        activeTabIndex -= 1;
    }

    public boolean isLastSection() {
        if (activeTabIndex == lastTabIndex) {
            return true;
        }
        return false;
    }

    public boolean isFirstSection() {
        if (activeTabIndex == 0) {
            return true;
        }
        return false;
    }
}