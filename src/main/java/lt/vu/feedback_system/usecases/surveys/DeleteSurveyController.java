package lt.vu.feedback_system.usecases.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.surveys.SurveyLogic;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DeleteSurveyController implements Serializable {

    @Getter
    @Setter
    private String link;

    @Inject
    private SurveyLogic surveyLogic;


    @Getter
    @Setter
    private Survey selectedSurvey;

    public void deleteSurvey(Survey survey) {
        surveyLogic.deleteSurvey(survey);
    }
}
