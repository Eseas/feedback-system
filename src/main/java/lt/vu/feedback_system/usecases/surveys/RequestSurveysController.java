package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.entities.User;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Named
@ViewScoped
public class RequestSurveysController implements Serializable {
    @Inject
    private UserContext userContext;

    @Getter
    @Setter
    private Boolean adminMode;

    @Getter
    @Setter
    private Integer userId;

    private User user;


    @Inject
    private SurveyLogic surveyLogic;

    @PostConstruct
    public void loadUser() {
        user = userContext.getUser();
    }

    public List<Survey> getSurveys() {
        if (adminMode)
            return surveyLogic.getAllSurveys();
        else
            return surveyLogic.getUserSurveys(user);
    }
}