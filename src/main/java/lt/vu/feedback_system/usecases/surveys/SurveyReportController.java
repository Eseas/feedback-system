package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.AnsweredSurveyDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.*;
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

        survey = surveyDAO.getSurveyByLink(link);

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
    public List<String> getUniqueQuestionRadioAnswers(Question q) {
        List<RadioAnswer> answers = answerDAO.getAllRadioAnswersByQuestionId(q.getId());
        List<String> result = new ArrayList<>();
        Boolean temp= false;
        for (RadioAnswer answer: answers) {
            for (String a: result) {
                if(answer.getRadioButton().getTitle().equals(a)){
                    temp = true;
                }
            }
            if (!temp){
                result.add(answer.getRadioButton().getTitle());
            }
            temp = false;
        }
        return result;
    }

    public List<String> getUniqueQuestionCheckboxAnswers(Question q) {
        List<CheckboxAnswer> answers = answerDAO.getAllCheckboxAnswersByQuestionId(q.getId());
        List<String> result = new ArrayList<>();
        Boolean temp= false;
        for (CheckboxAnswer answer: answers) {
            for(SelectedCheckbox answer2 : answer.getSelectedCheckboxes()){
                for (String a: result) {
                    if (answer2.getCheckbox().getTitle().equals(a)) {
                        temp = true;
                    }
                }
                if (!temp) {
                    result.add(answer2.getCheckbox().getTitle());
                }
                temp = false;
            }
        }
        return result;
    }

    public List<CheckboxAnswer> getQuestionCheckboxAnswers(Question q) {
        return answerDAO.getAllCheckboxAnswersByQuestionId(q.getId());
    }
    public double getAverage(Question q) {
        double sum = 0;
        double divider = 0;
        List<SliderAnswer> answers = answerDAO.getAllSliderAnswersByQuestionId(q.getId());
        for ( SliderAnswer answer : answers) {
            sum += answer.getValue();
            divider++;
        }
        return sum/divider;
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
    }public List<Integer> getMode(Question q) {
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

    public int countRadioAnswers(String title, Question q){
        List<RadioAnswer> answers = answerDAO.getAllRadioAnswersByQuestionId(q.getId());

        int count = 0;
        for (RadioAnswer answer: answers) {
            if(answer.getRadioButton().getTitle().equals(title)){
                count ++;
            }
        }
        return count;
    }
    public int countCheckBoxAnswers(String title, Question q){
        List<CheckboxAnswer> answers = answerDAO.getAllCheckboxAnswersByQuestionId(q.getId());

        int count = 0;
        for (CheckboxAnswer answer: answers) {
            for(SelectedCheckbox answer2: answer.getSelectedCheckboxes()) {
                if (answer2.getCheckbox().getTitle().equals(title)) {
                    count++;
                }
            }
        }
        return count;
    }
    public double getPercentCheckBoxAnswer(String title, Question q){
        List<CheckboxAnswer> answers = answerDAO.getAllCheckboxAnswersByQuestionId(q.getId());
        double answerCount = countCheckBoxAnswers(title,q);
        int allCount= 0;
        for (CheckboxAnswer answer: answers) {
            for(SelectedCheckbox answer2: answer.getSelectedCheckboxes()) {
                    allCount++;
            }
        }
        return answerCount/allCount * 100;
    }
    public double getPercentRadioAnswer(String title, Question q){
        List<RadioAnswer> answers = answerDAO.getAllRadioAnswersByQuestionId(q.getId());
        double answerCount = countRadioAnswers(title,q);
        int allCount= 0;
        for (RadioAnswer answer: answers) {
            allCount++;
        }
        return answerCount/allCount * 100;
    }
}