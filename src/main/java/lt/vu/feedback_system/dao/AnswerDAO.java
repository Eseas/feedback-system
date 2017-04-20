package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.answers.OptionAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class AnswerDAO {
    @Inject
    private EntityManager em;

    public void create(TextAnswer a) {
        em.persist(a);
    }

    public void create(SliderAnswer a) {
        em.persist(a);
    }

    public void create(OptionAnswer a) {
        em.persist(a);
    }
}
