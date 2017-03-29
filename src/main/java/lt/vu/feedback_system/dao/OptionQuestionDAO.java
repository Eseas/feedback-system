package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.OptionQuestion;
import lt.vu.feedback_system.entities.TextQuestion;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class OptionQuestionDAO {
    @Inject
    private EntityManager em;

    public void create(OptionQuestion optionQuestion) {
        em.persist(optionQuestion);
    }

    public List<OptionQuestion> getAllOptionQuestions() {
        return em.createNamedQuery("OptionQuestion.findAll", OptionQuestion.class).getResultList();
    }
}
