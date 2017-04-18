package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.questions.OptionQuestion;
import lt.vu.feedback_system.entities.questions.SliderQuestion;
import lt.vu.feedback_system.entities.questions.TextQuestion;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class QuestionDAO {
    @Inject
    private EntityManager em;

    public void create(TextQuestion q) {
        em.persist(q);
    }

    public void create(SliderQuestion q) {
        em.persist(q);
    }

    public void create(OptionQuestion q) {
        em.persist(q);
    }
}
