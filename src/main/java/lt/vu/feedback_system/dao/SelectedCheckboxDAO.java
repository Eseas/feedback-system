package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.answers.SelectedCheckbox;
import lt.vu.feedback_system.entities.questions.Checkbox;
import lt.vu.feedback_system.entities.questions.Question;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class SelectedCheckboxDAO {
    @Inject
    private EntityManager em;

    public void create(SelectedCheckbox selectedCheckbox) {
        em.persist(selectedCheckbox);
    }
    public SelectedCheckbox getSelectedCheckboxById(Integer id) {
        return em.createNamedQuery("SelectedCheckbox.findById", SelectedCheckbox.class).setParameter("id", id).getSingleResult();
    }

    public List<SelectedCheckbox> getSelectedCheckboxesByQuestion(Question question) {
        return em.createNamedQuery("SelectedCheckbox.findByQuestionId", SelectedCheckbox.class).setParameter("id", question.getId()).getResultList();
    }

    public List<SelectedCheckbox> getSelectedCheckboxesByCheckbox(Checkbox checkbox) {
        return em.createNamedQuery("SelectedCheckbox.findByCheckboxId", SelectedCheckbox.class).setParameter("id", checkbox.getId()).getResultList();
    }
}
