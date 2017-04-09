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
    public void merge(User user) {
        em.merge(user);

    }
    public void delete(User user) {
        em.persist(user);
    }

    public User getUserById(int id) {

        return em.find(User.class, id);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
    public List<User> getAllUsers() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }
}
