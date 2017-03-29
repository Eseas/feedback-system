package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.SliderQuestion;
import lt.vu.feedback_system.entities.TextQuestion;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class SliderQuestionDAO {
    @Inject
    private EntityManager em;

    public void create(SliderQuestion sliderQuestion) {
        em.persist(sliderQuestion);
    }

    public List<SliderQuestion> getAllSliderQuestions() {
        return em.createNamedQuery("SliderQuestion.findAll", SliderQuestion.class).getResultList();
    }
}
