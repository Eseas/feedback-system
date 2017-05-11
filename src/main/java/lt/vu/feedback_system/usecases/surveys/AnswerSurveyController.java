package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.usecases.users.NavigationBean;

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
    private String link;

    @Getter
    @Setter
    private Integer activeTabIndex;

    private Integer lastTabIndex;

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

    @Inject
    private NavigationBean navigationBean;

    @Getter
    private AnsweredSurvey answeredSurvey = new AnsweredSurvey();


    public void loadData() {

        answeredSurvey.setSurvey(surveyLogic.loadSurvey(link));
        for (Section section : answeredSurvey.getSurvey().getSections()) {
            surveyLogic.createEmptyAnswersForSection(section);
        }

        activeTabIndex = 0;
        lastTabIndex = answeredSurvey.getSurvey().getSections().size() - 1; // zero base
    }

    @Transactional
    public String answer() {
        answeredSurveyDAO.create(answeredSurvey);
        for(Section section : answeredSurvey.getSurvey().getSections()) {
            for (Answer answer : section.getAnswers())
                answerDAO.create(answer);
        }
//        return "surveys?faces-redirect=true";
//        return navigationBean.redirectTo("/WEB-INF/thanks-for-answer.html");
        return "/WEB-INF/thanks-for-answer.html";
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