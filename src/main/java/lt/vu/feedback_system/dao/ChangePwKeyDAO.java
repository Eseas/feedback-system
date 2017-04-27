package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.ChangePwKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ChangePwKeyDAO {

    @Inject
    private EntityManager em;

    public void create(ChangePwKey key) { em.persist(key); }

    public ChangePwKey selectByCode(String code) {
        return em.createNamedQuery("ChangePwKey.selectByCode", ChangePwKey.class)
                .setParameter("code", code).getSingleResult();
    }

    public int deleteByUserId(int userId) {
        return em.createNamedQuery("ChangePwKey.deleteByUserId")
                .setParameter("user_id", userId).executeUpdate();
    }

    public void delete(ChangePwKey changePwKey) { em.remove(changePwKey); }

}
