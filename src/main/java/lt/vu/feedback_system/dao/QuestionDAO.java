package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.entities.surveys.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class QuestionDAO {
    @Inject
    private EntityManager em;

    public void create(Question question) {
        em.persist(question);
    }

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

    public void update(TextQuestion q) {
        em.merge(q);
    }

    public void update(SliderQuestion q) {
        em.merge(q);
    }

    public void update(CheckboxQuestion q) {
        em.merge(q);
    }

    public void update(RadioQuestion q) {
        em.merge(q);
    }

    public List<TextQuestion> getTextQuestions(Section section) {
        return em.createNamedQuery("TextQuestion.findBySectionId", TextQuestion.class).setParameter("section_id", section.getId()).getResultList();
    }

    public List<SliderQuestion> getSliderQuestions(Section section) {
        return em.createNamedQuery("SliderQuestion.findBySectionId", SliderQuestion.class).setParameter("section_id", section.getId()).getResultList();
    }

    public List<RadioQuestion> getRadioQuestions(Section section) {
        return em.createNamedQuery("RadioQuestion.findBySectionId", RadioQuestion.class).setParameter("section_id", section.getId()).getResultList();
    }

    public List<CheckboxQuestion> getCheckboxQuestions(Section section) {
        return em.createNamedQuery("CheckboxQuestion.findBySectionId", CheckboxQuestion.class).setParameter("section_id", section.getId()).getResultList();
    }
}
