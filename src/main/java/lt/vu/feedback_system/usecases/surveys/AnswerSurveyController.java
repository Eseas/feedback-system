package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.interceptors.Logged;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.Checkbox;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
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
    private boolean preview;

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
            surveyLogic.createEmptyAnswersForSection(answeredSurvey, section);
        }

        activeTabIndex = 0;
        lastTabIndex = answeredSurvey.getSurvey().getSections().size() - 1; // zero base
    }

    @Logged
    @Transactional
    public String answer() {
        for(Section section : answeredSurvey.getSurvey().getSections()) {
            List<Answer> setAnswers = filterOutUnsetAnswers(section.getAnswers());
            if (setAnswers.size() > 0) {
                answeredSurveyDAO.create(answeredSurvey);
                for (Answer answer : setAnswers)
                    answerDAO.create(answer);
            }
        }
        return navigationBean.toThanksForAnswer();
    }

    private List<Answer> filterOutUnsetAnswers(List<Answer> answers) {
        List<Answer> goodAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            switch (answer.getQuestion().getType()) {
                case "TextQuestion":
                    if (!((TextAnswer) answer).getValue().isEmpty()) {
                        goodAnswers.add(answer);
                    }
                    break;
                case "RadioQuestion":
                    if (((RadioAnswer) answer).getRadioButton() != null) {
                        goodAnswers.add(answer);
                    }
                    break;
                case "CheckboxQuestion":
                    if (((CheckboxAnswer) answer).getSelectedCheckboxes().size() > 0) {
                        goodAnswers.add(answer);
                    }
                    break;
                case "SliderQuestion":
                    if (((SliderAnswer) answer).getValue() != null) {
                        goodAnswers.add(answer);
                    }
                    break;
                default:
                    break;
            }
        }
        return goodAnswers;
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
        return activeTabIndex == lastTabIndex;
    }

    public boolean isFirstSection() {
        return activeTabIndex == 0;
    }
}