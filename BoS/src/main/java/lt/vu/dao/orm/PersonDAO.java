package lt.vu.dao.orm;

import lt.vu.entities.jpa.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class PersonDAO {
    @Inject
    private EntityManager em;

    public void create(Person person) {
        em.persist(person);
    }
    public void merge(Person person) {
        em.merge(person);

    }
    public void delete(Person person) {
        em.persist(person);
    }

    public Person getPersonById(int id) {
        return em.find(Person.class, id);
    }
    public List<Person> getAllPeople() {
        return em.createNamedQuery("Person.findAll", Person.class).getResultList();
    }
}
