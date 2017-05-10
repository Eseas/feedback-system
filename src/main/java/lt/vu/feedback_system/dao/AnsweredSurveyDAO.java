package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AnsweredSurveyDAO {
    @Inject
    private EntityManager em;

    public void create(AnsweredSurvey answeredSurvey) {
        em.persist(answeredSurvey);
    }

    public List<AnsweredSurvey> getAllAnsweredSurveys() {
        return em.createNamedQuery("AnsweredSurvey.findAll", AnsweredSurvey.class).getResultList();
    }

    public AnsweredSurvey getAnsweredSurveyById(Integer id) {
        return em.createNamedQuery("AnsweredSurvey.findById", AnsweredSurvey.class).setParameter("id", id).getSingleResult();
    }

    public List<AnsweredSurvey> getAnsweredSurveysBySurveyId(Integer id) {
        return em.createNamedQuery("AnsweredSurvey.findAllBySurveyId", AnsweredSurvey.class).setParameter("id", id).getResultList();
    }
}
