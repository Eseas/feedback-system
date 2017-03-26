package lt.vu.dao.orm;

import lt.vu.entities.jpa.Question;
import lt.vu.entities.jpa.Survey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class QuestionDAO {
    @Inject
    private EntityManager em;

    public void create(Question question) {
        em.persist(question);
    }

    public List<Question> getAllQuestions() {
        return em.createNamedQuery("Question.findAll", Question.class).getResultList();
    }
}
