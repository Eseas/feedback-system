package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.questions.CheckboxQuestion;
import lt.vu.feedback_system.entities.questions.RadioQuestion;
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

    public void create(CheckboxQuestion q) {
        em.persist(q);
    }

    public void create(RadioQuestion q) {
        em.persist(q);
    }
}
