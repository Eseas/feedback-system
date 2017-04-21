package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.questions.Checkbox;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

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
}
