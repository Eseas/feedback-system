package lt.vu.feedback_system.entities.answers;

import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;

public interface Answer {
    Question getQuestion();

    AnsweredSurvey getAnsweredSurvey();

    void setAnsweredSurvey(AnsweredSurvey answeredSurvey);

}
