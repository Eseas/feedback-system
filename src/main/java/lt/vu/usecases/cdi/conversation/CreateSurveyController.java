package lt.vu.usecases.cdi.conversation;

import lombok.extern.slf4j.Slf4j;
import lt.vu.dao.SurveyDAO;
import lt.vu.entities.Survey;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
@Slf4j
public class CreateSurveyController implements Serializable {
//
//    private static final String PAGE_INDEX_REDIRECT = "surveys?faces-redirect=true";
//
//    private enum CURRENT_FORM {
//        CREATE_SURVEY, ASSIGN_PERSON, CREATE_QUESTION, CONFIRMATION
//    }
//
//    @Inject
//    private EntityManager em;
//
//    @Inject
//    @Getter
//    private Conversation conversation;
//
    @Inject
    private SurveyDAO surveyDAO;
//    @Inject
//    private QuestionDAO questionDAO;
//    @Inject
//    private UserDAO userDAO;
//
//    @Getter
//    private Survey survey = new Survey();
//    @Getter
//    private List<Question> questions = new ArrayList<>();
//    @Getter
//    private User user;
//    @Getter
//    private List<User> assignees = new ArrayList<>();
//
//    @Setter
//    @Getter
//    private String email;
//
//    private CURRENT_FORM currentForm = CURRENT_FORM.CREATE_SURVEY;
//    public boolean isCurrentForm(CURRENT_FORM form) {
//        return currentForm == form;
//    }
//
//    /**
//     * The first conversation step.
//     */
//    public void createSurvey() {
//        System.out.println("I have survey " + survey);
//        conversation.begin();
//        System.out.println("Now I have survey " + survey);
//        currentForm = CURRENT_FORM.ASSIGN_PERSON;
//    }
//
//    /**
//     * The second conversation step.
//     */
//    public void assignUserByEmail() {
//        List<User> people = userDAO.getAllPeople();
//        for (User tempP:  people) {
////            Jei user fieldai nuliniai, tada tyliai nucrashina
//            System.out.println("mano id= " + tempP.getId());
//            if (tempP.getEmail().equals(email)) {
//                assignees.add(tempP);
//                tempP.getSurveyList().add(survey);
//                survey.getUserList().add(tempP);
//                break;
//            }
//        }
//        email = "kuku";
////        currentForm = CURRENT_FORM.CONFIRMATION;
//    }
//
//    public void quitPeopleAssigning() {
//        currentForm = CURRENT_FORM.CREATE_QUESTION;
//    }
//
//    /**
//     * The 3rd conversation step.
//     */
//    public void createQuestion(String questionText) {
//        Question question = new Question();
//        question.setText(questionText);
//        question.setSurveyId(survey);
//
//        survey.getQuestionList().add(question);
//
//        questions.add(question);
//    }
//
//    public void quitQuestionCreation() {
//        currentForm = CURRENT_FORM.CONFIRMATION;
//    }
//
//    /**
//     * The last conversation step.
//     */
//    @Transactional(Transactional.TxType.REQUIRED)
//    public String ok() {
//        try {
//            surveyDAO.create(survey);
//            for (User p:
//                 assignees) {
//                User foundP = userDAO.getUserById(p.getId());
//                userDAO.merge(foundP);
//            }
//            for (Question q: questions
//                 ) {
//                questionDAO.create(q);
//            }
//            em.flush();
//            Messages.addGlobalInfo("Success!");
//        } catch (OptimisticLockException ole) {
//            // Other user was faster...
//            Messages.addGlobalWarn("Please try again");
//            log.warn("Optimistic Lock violated: ", ole);
//        } catch (PersistenceException pe) {
//            // Some problems with DB - most often this is programmer's fault.
//            Messages.addGlobalError("Finita la commedia...");
//            log.error("Error ending conversation: ", pe);
//        }
//        Faces.getFlash().setKeepMessages(true);
//        conversation.end();
//        return PAGE_INDEX_REDIRECT;
//    }
//
//    /**
//     * The last (alternative) conversation step.
//     */
//    public String cancel() {
//        conversation.end();
//        return PAGE_INDEX_REDIRECT;
//    }
//
//
//
//    public List<User> getAllPeople() {
//        return userDAO.getAllPeople();
//    }
    public List<Survey> getAllSurveys() {
        return surveyDAO.getAllSurveys();
    }
}