package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.AnsweredSurvey;
import lt.vu.feedback_system.entities.Survey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AnsweredSurveyDAO {
    @Inject
    private EntityManager em;

    public void create(AnsweredSurvey answer) {
        em.persist(answer);
    }

    public List<AnsweredSurvey> getAllAnswers() {
        return em.createNamedQuery("AnsweredSurvey.findAll", AnsweredSurvey.class).getResultList();
    }

}