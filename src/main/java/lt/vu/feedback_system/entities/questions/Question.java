package lt.vu.feedback_system.entities.questions;

import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;

import java.util.List;

public interface Question {
    Integer getId();

    void setId(Integer id);

    String getType();

    String getTitle();

    void setTitle(String title);

    Integer getPosition();

    void setPosition(Integer position);

    Boolean getRequired();

    void setRequired(Boolean value);

    Section getSection();

    void setSection(Section section);

    Survey getSurvey();

    void setSurvey(Survey survey);

    // FRESH FUNCTIONALITY (Kazimieras added)
    List<? extends Answer> getAnswers();

}
