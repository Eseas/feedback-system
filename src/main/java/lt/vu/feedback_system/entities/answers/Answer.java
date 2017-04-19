package lt.vu.feedback_system.entities.answers;

import lt.vu.feedback_system.entities.questions.Question;

/**
 * Created by kazim on 2017-04-04.
 */
public interface Answer {
    public String getType();

    public Question getQuestion();

}
