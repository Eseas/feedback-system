package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.User;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class UserDAO {

    @Inject
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void updateAndFlush(User user) {
        em.merge(user);
        em.flush();
    }

    public boolean userExists(String email) {
        return em.createNamedQuery("User.countByEmail", Long.class).setParameter("email", email.toLowerCase()).getSingleResult() > 0;
    }

    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    public User getUserByEmail(String email) {
        return em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email.toLowerCase())
                .getSingleResult();
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }
}
