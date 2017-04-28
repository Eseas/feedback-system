package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.RegKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class RegKeyDAO {

    @Inject
    private EntityManager em;

    public void create(RegKey key) { em.persist(key); }

    public RegKey selectByCode(String code) {
        return em.createNamedQuery("RegKey.selectByCode", RegKey.class)
                .setParameter("code", code).getSingleResult();
    }

    public int deleteByUserId(int userId) {
        return em.createNamedQuery("RegKey.deleteByUserId")
                .setParameter("user_id", userId).executeUpdate();
    }

    public void delete(RegKey regKey) { em.remove(regKey); }

}
