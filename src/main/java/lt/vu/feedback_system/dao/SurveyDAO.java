package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.surveys.Survey;

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

    public void update(Survey survey) {
        em.merge(survey);
    }

    public void delete(Survey survey) {
        em.remove(em.contains(survey) ? survey : em.merge(survey));
    }

    public List<Survey> getAllSurveys() {
        return em.createNamedQuery("Survey.findAll", Survey.class).getResultList();
    }

    public Survey getSurveyById(Integer id) {
        return em.createNamedQuery("Survey.findById", Survey.class).setParameter("id", id).getSingleResult();
    }

    public Survey getSurveyByLink(String link) {
        return em.createNamedQuery("Survey.findByLink", Survey.class).setParameter("link", link).getSingleResult();
    }

    public List<Survey> getSurveysByCreatorId(Integer id){
        return em.createNamedQuery("Survey.findAllByCreatorId", Survey.class).setParameter("id", id).getResultList();
    }

}
