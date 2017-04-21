package lt.vu.feedback_system.entity_utils;

import lt.vu.feedback_system.entities.answers.Answer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kazim on 2017-04-21.
 */
public class AnswerUtil {
    public static List<Answer> sort(List<Answer> answers) {
        Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer lhs, Answer rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                return lhs.getQuestion().getPosition() > rhs.getQuestion().getPosition() ? 1 : (lhs.getQuestion().getPosition() < rhs.getQuestion().getPosition() ) ? -1 : 0;
            }
        });
        return answers;
    }
}
