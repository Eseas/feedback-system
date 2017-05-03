package lt.vu.feedback_system.businesslogic.surveys;

import lt.vu.feedback_system.businesslogic.users.Session;
import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.QuestionDAO;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SurveyLogic {
    @Inject
    private AnswerDAO answerDAO;
    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private QuestionDAO questionDAO;

    @Inject
    private Session session;

    @Inject
    private QuestionLogic questionLogic;

    public Survey loadSurvey(Integer id) {
        Survey survey = surveyDAO.getSurveyById(id);

        for (Section section : survey.getSections())
            questionLogic.loadQuestionsToSection(section);

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

//    public List<Answer> getSectionAnswers(Section section) {
//        List<Answer> answers = new ArrayList<>();
//
//        answers.addAll(answerDAO.getTextAnswersBySection(section));
//        answers.addAll(answerDAO.getSliderAnswersBySection(section));
//        answers.addAll(answerDAO.getRadioAnswersBySection(section));
//        answers.addAll(answerDAO.getCheckboxAnswersBySection(section));
//
//        return answers;
//    }

    @Transactional
    public void create(Survey survey) {
        survey.setCreator(session.getUser());

        surveyDAO.create(survey);

        for(Section section : survey.getSections()) {
            for (Question question : section.getQuestions())
                questionDAO.create(question);
        }
    }
}
