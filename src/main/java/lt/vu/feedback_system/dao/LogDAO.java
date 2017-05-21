package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class LogDAO {

    @Inject
    private EntityManager em;

    public void create(Log log) {
        em.persist(log);
    }
}
