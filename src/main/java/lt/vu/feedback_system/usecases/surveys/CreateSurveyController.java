package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.*;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Slf4j
public class CreateSurveyController implements Serializable {

    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private TextQuestionDAO textQuestionDAO;
    @Inject
    private OptionQuestionDAO optionQuestionDAO;
    @Inject
    private SliderQuestionDAO sliderQuestionDAO;
    @Inject
    private OptionValueDAO optionValueDAO;

    @Getter
    private Survey survey = new Survey();

    @Getter
    private TextQuestion textQuestion = new TextQuestion();

    public List<Survey> getAllSurveys() {
        return surveyDAO.getAllSurveys();
    }

    /**
     * Text question...
     */
    public void addTextQuestion() {
//        TextQuestion textQuestion = new TextQuestion();
        textQuestion.setSurvey(survey);
        survey.getTextQuestionList().add(textQuestion);
        textQuestion = new TextQuestion();
    }

    public void removeTextQuestion(TextQuestion textQuestion) {
        survey.getTextQuestionList().remove(textQuestion);
    }


    /**
     * Option question...
     */
    public void addOptionQuestion() {
        OptionQuestion optionQuestion = new OptionQuestion();
        optionQuestion.setSurvey(survey);
        survey.getOptionQuestionList().add(optionQuestion);
    }

    public void removeOptionQuestion(OptionQuestion optionQuestion) {
        survey.getOptionQuestionList().remove(optionQuestion);
    }

    public void addOptionValue(OptionQuestion optionQuestion) {
        OptionValue optionValue = new OptionValue();
        optionQuestion.getOptionValueList().add(optionValue);
        optionValue.setQuestion(optionQuestion);
    }

    /**
     * Works only when optionQuestion and optionValue is not persisted.
     * @param optionQuestion
     * @param optionValue
     */
    public void removeOptionValue(OptionQuestion optionQuestion, OptionValue optionValue) {
        optionQuestion.getOptionValueList().remove(optionValue);
    }

    /**
     * Slider question...
     */
    public void addSliderQuestion() {
        SliderQuestion sliderQuestion = new SliderQuestion();
        sliderQuestion.setSurvey(survey);
        survey.getSliderQuestionList().add(sliderQuestion);
    }

    public void removeSliderQuestion(SliderQuestion sliderQuestion) {
        survey.getSliderQuestionList().remove(sliderQuestion);
    }



    @Transactional
    public String create() {
        for (TextQuestion textQuestion : survey.getTextQuestionList()) {
            textQuestionDAO.create(textQuestion);
        }
        for (OptionQuestion optionQuestion : survey.getOptionQuestionList()) {
            optionQuestionDAO.create(optionQuestion);
            for (OptionValue optionValue : optionQuestion.getOptionValueList()) {
                optionValueDAO.create(optionValue);
            }
        }
        for (SliderQuestion sliderQuestion : survey.getSliderQuestionList()) {
            sliderQuestionDAO.create(sliderQuestion);
        }
        surveyDAO.create(survey);
        return "surveys?faces-redirect=true";
    }
}