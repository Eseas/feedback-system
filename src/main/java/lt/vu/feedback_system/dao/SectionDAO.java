package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.surveys.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class SectionDAO {
    @Inject
    private EntityManager em;

    public void create(Section section) {
        em.persist(section);
    }

    public List<Section> getAllSectionsBySurveyId(Integer id) {
        return em.createNamedQuery("Section.findAll", Section.class).setParameter("id", id).getResultList();
    }

}
