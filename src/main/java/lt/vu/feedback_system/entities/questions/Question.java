package lt.vu.feedback_system.entities.questions;

/**
 * Created by kazim on 2017-04-04.
 */
public interface Question {
    public String getType();

    public String getTitle();
    public void setTitle(String title);

    public Integer getPosition();
    public void setPosition(Integer position);

//    public Survey getSurvey();
//    public void setSurvey(Survey survey);

}
