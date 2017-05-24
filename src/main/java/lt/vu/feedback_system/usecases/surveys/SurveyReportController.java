package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.reports.ChartLogic;
import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.AnsweredSurveyDAO;
import lt.vu.feedback_system.dao.SelectedCheckboxDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.questions.*;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class SurveyReportController implements Serializable {
    @Getter
    @Setter
    private String link;

    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private AnsweredSurveyDAO answeredSurveyDAO;

    @Inject
    private SelectedCheckboxDAO selectedCheckboxDAO;

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

    @Inject
    private ChartLogic chartLogic;

    public void loadData() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("surveyContext", surveyContext);

        survey = surveyDAO.getSurveyByLink(link);

        for (Section section : survey.getSections()) {
            surveyLogic.loadQuestionsToSection(section);
        }
    }

    public String createPieModel(RadioQuestion radioQuestion) {
        if (radioQuestion.getModel() == null) {
            radioQuestion.setModel(chartLogic.createPieModel(radioQuestion));

            return "LOADING";
        } else {
            if (radioQuestion.getModel().isDone()) {
                return "DONE";
            } else {
                return "LOADING";
            }
        }
    }

    public String createBarModel(CheckboxQuestion checkboxQuestion) {
        if (checkboxQuestion.getModel() == null) {
            checkboxQuestion.setModel(chartLogic.createBarModel(checkboxQuestion));

            return "LOADING";
        } else {
            if (checkboxQuestion.getModel().isDone()) {
                return "DONE";
            } else {
                return "LOADING";
            }
        }
    }

    public String createTagCloudModel(TextQuestion textQuestion) {
        if (textQuestion.getModel() == null) {
            textQuestion.setModel(chartLogic.createTagCloud(textQuestion));

            return "LOADING";
        } else {
            if (textQuestion.getModel().isDone()) {
                return "DONE";
            } else {
                return "LOADING";
            }
        }
    }

    public List<Question> getQuestions() {
        return Sorter.sortQuestionsAscending(questions);
    }

    public double getAverage(Question q) {
        double sum = 0;
        double divider = 0;
        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());
        for ( SliderAnswer answer : answers) {
            sum += answer.getValue();
            divider++;
        }
        return round(sum/divider);
    }

    public double getMedian(Question q) {

        double median;
        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());
        answers = Sorter.sortAnswersByValue(answers);
        if ((answers.size() % 2) == 0) {
            median = answers.get(answers.size() / 2 - 1).getValue() + answers.get(answers.size() / 2).getValue();
            median= median / 2;
        }
        else{
            median = answers.get(answers.size() / 2).getValue();
        }
        return median;
    }

    public List<Integer> getMode(Question q) {
        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());
        List<Integer> numbers = new ArrayList<>();
        for ( SliderAnswer answer : answers) {
            numbers.add(answer.getValue());
        }

        final Map<Integer, Long> countFrequencies = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final long maxFrequency = countFrequencies.values().stream()
                .mapToLong(count -> count)
                .max().orElse(-1);

        return countFrequencies.entrySet().stream()
                .filter(tuple -> tuple.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public int countRadioButtonAnswers(RadioButton radioButton){
        return answerDAO.getRadioAnswersByRadioButton(radioButton).size();
    }

    public int countCheckBoxAnswers(Checkbox checkbox){
        return selectedCheckboxDAO.getSelectedCheckboxesByCheckbox(checkbox).size();
    }

    // round a double to 2 decimal places
    private double round(double value){
        value = value*100;
        value = Math.round(value);
        value = value /100;
        return value;
    }

    public double getPercentCheckBoxAnswer(Checkbox checkbox){
        Integer allAnswerCount = selectedCheckboxDAO.getSelectedCheckboxesByQuestion(checkbox.getQuestion()).size();
        Integer checkboxAnswerCount = selectedCheckboxDAO.getSelectedCheckboxesByCheckbox(checkbox).size();

        if (allAnswerCount == 0)
            return 0;
        else
            return round((double)checkboxAnswerCount/allAnswerCount * 100);
    }

    public double getPercentRadioAnswer(RadioButton radioButton){
        Integer allAnswerCount = answerDAO.getAllRadioAnswersByQuestionId(radioButton.getQuestion().getId()).size();
        Integer radioButtonAnswerCount = answerDAO.getRadioAnswersByRadioButton(radioButton).size();

        if (allAnswerCount == 0)
            return 0;
        else
            return round((double)radioButtonAnswerCount/allAnswerCount * 100);
    }
}