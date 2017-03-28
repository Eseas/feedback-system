package lt.vu.feedback_system.usecases.surveys;

import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.entities.Survey;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Model
@Slf4j
public class CreateSurveyController implements Serializable {

    @Inject
    private SurveyDAO surveyDAO;

    public List<Survey> getAllSurveys() {
        return surveyDAO.getAllSurveys();
    }
}