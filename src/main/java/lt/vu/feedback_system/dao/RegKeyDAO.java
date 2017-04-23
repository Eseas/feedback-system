package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.RegKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class RegKeyDAO {

    @Inject
    private EntityManager em;

    public void create(RegKey key) { em.persist(key); }

    public RegKey getByCode(String code) {
        return em.createNamedQuery("RegKey.findByCode", RegKey.class)
                .setParameter("code", code).getSingleResult();
    }

    public List<RegKey> getAll() {
        return em.createNamedQuery("RegKey.findAll", RegKey.class).getResultList();
    }

}
