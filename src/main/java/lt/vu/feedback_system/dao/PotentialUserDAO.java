package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.User;

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

    public void merge(User user) {
        em.merge(user);
    }
    public void delete(PotentialUser potentialUser) {
        em.remove(potentialUser);
    }

    public User getUserById(int id) {
        return em.find(User.class, id);
    }
    public List<PotentialUser> getAllPotentialUsers() {
        return em.createNamedQuery("PotentialUser.findAll", PotentialUser.class).getResultList();
    }
}
