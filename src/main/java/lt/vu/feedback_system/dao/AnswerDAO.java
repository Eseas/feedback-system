package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
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

    public void create(RadioAnswer a) {
        em.persist(a);
    }

    public void create(CheckboxAnswer a) {
        em.persist(a);
    }
}
