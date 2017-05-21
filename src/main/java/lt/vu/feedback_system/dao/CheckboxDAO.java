package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.questions.Checkbox;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CheckboxDAO {
    @Inject
    private EntityManager em;

    public void create(Checkbox checkbox) {
        em.persist(checkbox);
    }
    public void update(Checkbox checkbox) {
        em.merge(checkbox);
    }
    public Checkbox getCheckboxById(Integer id) {
        return em.createNamedQuery("Checkbox.findById", Checkbox.class).setParameter("id", id).getSingleResult();
    }
}
