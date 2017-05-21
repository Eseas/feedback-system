package lt.vu.feedback_system.entities.answers;

import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;

public interface Answer {
    public Question getQuestion();

    public AnsweredSurvey getAnsweredSurvey();
    public void setAnsweredSurvey(AnsweredSurvey answeredSurvey);

}
