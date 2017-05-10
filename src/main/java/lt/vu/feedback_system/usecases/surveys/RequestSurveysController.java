package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RequestSurveysController implements Serializable {
    @Inject
    private UserContext userContext;

    @Getter
    @Setter
    private Boolean adminMode;

    @Inject
    private SurveyLogic surveyLogic;

    public List<Survey> getSurveys() {
        if (adminMode)
            return surveyLogic.getAllSurveys();
        else
            return surveyLogic.getUserSurveys(userContext.getUser());
    }
}