package lt.vu.feedback_system.entities.answers;

/**
 * Created by kazim on 2017-04-04.
 */
public interface Answer {
    public String getType();

    public String getTitle();
    public void setTitle(String title);

    public Integer getPosition();
    public void setPosition(Integer position);

    public Boolean getRequired();
    public void setRequired(Boolean value);
//    public Survey getSurvey();
//    public void setSurvey(Survey survey);

}
