package lt.vu.feedback_system.dao;

import lt.vu.feedback_system.entities.answers.OptionValueAnswer;
import lt.vu.feedback_system.entities.questions.OptionValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class OptionValueAnswerDAO {
    @Inject
    private EntityManager em;

    public void create(OptionValueAnswer optionValueAnswer) {
        em.persist(optionValueAnswer);
    }

}
