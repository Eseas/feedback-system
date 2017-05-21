package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.RadioButton;
import lt.vu.feedback_system.entities.surveys.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AnswerDAO {
    @Inject
    private EntityManager em;

    public void create(Answer a) {
        em.persist(a);
    }

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

    public List<TextAnswer> getTextAnswers(Section section) {
        return em.createNamedQuery("TextAnswer.findBySectionId", TextAnswer.class).setParameter("section_id", section.getId()).getResultList();
    }

    public List<SliderAnswer> getSliderAnswers(Section section) {
        return em.createNamedQuery("SliderAnswer.findBySectionId", SliderAnswer.class).setParameter("section_id", section.getId()).getResultList();
    }

    public List<RadioAnswer> getRadioAnswers(Section section) {
        return em.createNamedQuery("RadioAnswer.findBySectionId", RadioAnswer.class).setParameter("section_id", section.getId()).getResultList();
    }

    public List<RadioAnswer> getRadioAnswersByRadioButton(RadioButton radioButton) {
        return em.createNamedQuery("RadioAnswer.findByRadioButtonId", RadioAnswer.class).setParameter("id", radioButton.getId()).getResultList();
    }

    public List<CheckboxAnswer> getCheckboxAnswers(Section section) {
        return em.createNamedQuery("CheckboxAnswer.findBySectionId", CheckboxAnswer.class).setParameter("section_id", section.getId()).getResultList();
    }
}
