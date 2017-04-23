package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.users.Session;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.entities.questions.*;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Named
@ViewScoped
public class RequestSurveysController implements Serializable {
    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private Session session;

    private User user;

    @PostConstruct
    public void loadUser() {
        user = session.getUser();
    }

    public List<Survey> getAllSurveys(){
        return surveyDAO.getAllSurveys();
    }

    public List<Survey> getAllUserSurveys(){
        return surveyDAO.getSurveysByCreatorId(user.getId());
    }
}