package lt.vu.feedback_system.utils;

import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.questions.Question;
import java.util.*;

public class Sorter {
    public static List<Answer> sortAnswersAscending(List<Answer> answers) {
        Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer lhs, Answer rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                return lhs.getQuestion().getPosition() > rhs.getQuestion().getPosition() ? 1 : (lhs.getQuestion().getPosition() < rhs.getQuestion().getPosition() ) ? -1 : 0;
            }
        });
        return answers;
    }

    public static List<Question> sortQuestionsAscending(List<Question> questions) {
        Collections.sort(questions, new Comparator<Question>() {
            @Override
            public int compare(Question lhs, Question rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                return lhs.getPosition() > rhs.getPosition() ? 1 : (lhs.getPosition() < rhs.getPosition() ) ? -1 : 0;
            }
        });
        return questions;
    }
    public static List<SliderAnswer> sortAnswersByValue(List<SliderAnswer> answers){
        Collections.sort(answers, new Comparator<SliderAnswer>() {
            @Override
            public int compare(SliderAnswer lhs, SliderAnswer rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                return lhs.getValue() > rhs.getValue() ? 1 : (lhs.getValue() < rhs.getValue() ) ? -1 : 0;
            }
        });
        return answers;
    }
    public static List<Map.Entry<String, Integer>> sortMapByValue(Map map){
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
                set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }
}
