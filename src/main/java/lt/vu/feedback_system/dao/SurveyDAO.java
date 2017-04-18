package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.Survey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class SurveyDAO {
    @Inject
    private EntityManager em;

    public void create(Survey survey) {
        em.persist(survey);
    }

    public List<Survey> getAllSurveys() {
        return em.createNamedQuery("Survey.findAll", Survey.class).getResultList();
    }
    public Survey getSurveyByName(String title) {
        return em.createNamedQuery("Survey.findByName", Survey.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public Survey getSurveyById(Integer id) {
        return em.createNamedQuery("Survey.findById", Survey.class).setParameter("id", id).getSingleResult();
    }
}
