package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.questions.Question;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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

    public List<TextAnswer> getAllTextAnswersByQuestionId(Integer id) {
        return em.createNamedQuery("TextAnswer.findAllByQuestionId", TextAnswer.class).setParameter("id", id).getResultList();
    }

    public List<SliderAnswer> getAllSliderAnswersByQuestionId(Integer id) {
        return em.createNamedQuery("SliderAnswer.findAllByQuestionId", SliderAnswer.class).setParameter("id", id).getResultList();
    }

    public List<RadioAnswer> getAllRadioAnswersByQuestionId(Integer id) {
        return em.createNamedQuery("RadioAnswer.findAllByQuestionId", RadioAnswer.class).setParameter("id", id).getResultList();
    }

    public List<CheckboxAnswer> getAllCheckboxAnswersByQuestionId(Integer id) {
        return em.createNamedQuery("CheckboxAnswer.findAllByQuestionId", CheckboxAnswer.class).setParameter("id", id).getResultList();
    }
}
