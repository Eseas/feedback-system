package lt.vu.feedback_system.entities.questions;

import lt.vu.feedback_system.entities.surveys.Section;

public interface Question {
    public Integer getId();
    public void setId(Integer id);

    public String getType();

    public String getTitle();
    public void setTitle(String title);

    public Integer getPosition();
    public void setPosition(Integer position);

    public Boolean getRequired();
    public void setRequired(Boolean value);

    public Section getSection();
    public void setSection(Section section);

}
