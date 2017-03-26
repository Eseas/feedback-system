package lt.vu.dao.orm;

import lt.vu.entities.jpa.User;

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
    public List<User> getAllPeople() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }
}
