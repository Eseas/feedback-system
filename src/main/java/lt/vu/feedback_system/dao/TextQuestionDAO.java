package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.TextQuestion;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class TextQuestionDAO {
    @Inject
    private EntityManager em;

    public void create(TextQuestion textQuestion) {
        em.persist(textQuestion);
    }

    public List<TextQuestion> getAllTextQuestions() {
        return em.createNamedQuery("TextQuestion.findAll", TextQuestion.class).getResultList();
    }
}
