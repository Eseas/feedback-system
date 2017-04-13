package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.*;
import lt.vu.feedback_system.entities.*;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Slf4j
public class SurveyReportController implements Serializable {

    @Inject
    private SurveyDAO surveyDAO;
    @Inject
    private AnsweredSurveyDAO answeredSurveyDAO;
    @Getter
    private AnsweredSurvey answeredSurvey = new AnsweredSurvey();

    public List<AnsweredSurvey> getAllAnswers() {return answeredSurveyDAO.getAllAnswers();}


}