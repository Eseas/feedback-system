package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.config.Configuration;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

@Named
@ViewScoped
public class RequestSurveysController implements Serializable {
    @Inject
    private UserContext userContext;

    @Inject
    private Configuration config;

    private Properties props;

    @Getter
    @Setter
    private Boolean adminMode = false;

    @Inject
    private SurveyLogic surveyLogic;

    @Getter
    @Setter
    private Survey selectedSurvey;

    public List<Survey> getSurveys() {
        if (userContext.isAdmin() && adminMode)
            return surveyLogic.getAllSurveys();
        else
            return surveyLogic.getUserSurveys(userContext.getUser());
    }

    @PostConstruct
    public void loadProps() {
        props = config.getProps();
    }

    public String formLink(final String page, final String link) {
        this.props = config.getProps();
        return String.format("%s/%s?s=%s", props.getProperty("common.baseURL"), page, link);
    }
}