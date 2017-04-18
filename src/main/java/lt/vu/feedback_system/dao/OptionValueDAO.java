package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.questions.OptionValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class OptionValueDAO {
    @Inject
    private EntityManager em;

    public void create(OptionValue optionValue) {
        em.persist(optionValue);
    }

    public List<OptionValue> getAllOptionValues() {
        return em.createNamedQuery("OptionValue.findAll", OptionValue.class).getResultList();
    }
}
