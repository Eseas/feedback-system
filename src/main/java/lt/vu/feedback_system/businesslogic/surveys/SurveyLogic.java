package lt.vu.feedback_system.businesslogic.surveys;

import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.Sorter;
import lt.vu.feedback_system.utils.generate.Hash;
import lt.vu.feedback_system.utils.generate.HashGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class SurveyLogic {
    @Inject
    private AnswerDAO answerDAO;
    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private QuestionDAO questionDAO;
    @Inject
    private UserContext userContext;
    @Inject
    @Hash
    private HashGenerator hashGenerator;


    public Survey loadSurvey(Integer id) {
        Survey survey = surveyDAO.getSurveyById(id);

        for (Section section : survey.getSections())
            loadQuestionsToSection(section);

        return survey;
    }

    public void addSection(Survey survey) {
        Integer position = getNewSectionPosition(survey);

        Section section = new Section();
        section.setPosition(position);

        section.setSurvey(survey);
        survey.getSections().add(section);
    }

    public Integer getNewSectionPosition(Survey survey) {
        return survey.getSections().size() + 1;
    }


    @Transactional
    public void create(Survey survey) {
        Random rand = new Random();

        survey.setCreator(userContext.getUser());
        survey.setLink(hashGenerator.hash(Integer.toString(rand.nextInt())));

        surveyDAO.create(survey);

        for(Section section : survey.getSections()) {
            for (Question question : section.getQuestions())
                questionDAO.create(question);
        }
    }

    public void loadQuestionsToSection(Section section) {

        section.getQuestions().clear();

        section.getQuestions().addAll(questionDAO.getTextQuestions(section));
        section.getQuestions().addAll(questionDAO.getSliderQuestions(section));
        section.getQuestions().addAll(questionDAO.getRadioQuestions(section));
        section.getQuestions().addAll(questionDAO.getCheckboxQuestions(section));

        Sorter.sortQuestionsAscending(section.getQuestions());
    }

    public void loadAnswersToSection(Section section) {
        section.getAnswers().clear();

        section.getAnswers().addAll(answerDAO.getTextAnswers(section));
        section.getAnswers().addAll(answerDAO.getSliderAnswers(section));
        section.getAnswers().addAll(answerDAO.getRadioAnswers(section));
        section.getAnswers().addAll(answerDAO.getCheckboxAnswers(section));
    }

    public void createEmptyAnswersForSection(Section section) {
        for(Question question : section.getQuestions()) {
            switch (question.getType()) {
                case "TextQuestion":
                    TextAnswer textAnswer = new TextAnswer();
                    textAnswer.setQuestion((TextQuestion) question);
                    section.getAnswers().add(textAnswer);
                    break;
                case "SliderQuestion":
                    SliderAnswer sliderAnswer = new SliderAnswer();
                    sliderAnswer.setQuestion((SliderQuestion) question);
                    section.getAnswers().add(sliderAnswer);
                    break;
                case "RadioQuestion":
                    RadioAnswer radioAnswer = new RadioAnswer();
                    radioAnswer.setQuestion((RadioQuestion) question);
                    section.getAnswers().add(radioAnswer);
                    break;
                case "CheckboxQuestion":
                    CheckboxAnswer checkboxAnswer = new CheckboxAnswer();
                    checkboxAnswer.setQuestion((CheckboxQuestion) question);
                    section.getAnswers().add(checkboxAnswer);
                    break;
            }
        }
        Sorter.sortAnswersAscending(section.getAnswers());
    }


    public void moveUp(Section section, Question q) {

        if (q.getPosition() == 1)
            return;

        for (Question tempQ : section.getQuestions()) {
            if (tempQ.getPosition() + 1 == q.getPosition()) {
                q.setPosition(q.getPosition() - 1);
                tempQ.setPosition(tempQ.getPosition() + 1);
                break;
            }
        }
    }

    public void moveDown(Section section, Question q) {

        if (q.getPosition() == section.getQuestions().size())
            return;

        for (Question tempQ : section.getQuestions()) {
            if (tempQ.getPosition() != q.getPosition())
                if (tempQ.getPosition() == (q.getPosition() + 1)) {
                    q.setPosition(q.getPosition() + 1);
                    tempQ.setPosition(tempQ.getPosition() - 1);
                    break;
                }
        }
    }

    public Integer getNewQuestionPosition(Section section) {
        return section.getQuestions().size() + 1;
    }

    public void addQuestion(Section section, Question question) {
        Integer position = getNewQuestionPosition(section);

        question.setRequired(false);
        question.setPosition(position);

        question.setSurvey(section.getSurvey());
        question.setSection(section);
        section.getQuestions().add(question);
    }

}
