package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.PotentialUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class PotentialUserDAO {

    @Inject
    private EntityManager em;

    public void create(PotentialUser potentialUser) {
            em.persist(potentialUser);
    }

    public void delete(PotentialUser potentialUser) {
        em.remove(potentialUser);
    }

    public List<PotentialUser> getAllPotentialUsers() {
        return em.createNamedQuery("PotentialUser.findAll", PotentialUser.class).getResultList();
    }

    public PotentialUser selectByEmail(String email) {
        return em.createNamedQuery("PotentialUser.selectByEmail", PotentialUser.class)
                .setParameter("email", email).getSingleResult();
    }

}
