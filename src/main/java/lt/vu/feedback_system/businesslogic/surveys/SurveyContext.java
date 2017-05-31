package lt.vu.feedback_system.businesslogic.surveys;

import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.dao.SurveyDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.Calendar;

@ApplicationScoped
public class SurveyContext implements Serializable {

    private static final long serialVersionUID = 4458465415649870L;

    @Inject
    private SurveyDAO surveyDAO;

    @Inject
    private UserContext userContext;

    public boolean isSurveyCreator(String surveyLink) {
        try {
            Integer surveyCreatorId = surveyDAO.getSurveyByLink(surveyLink).getCreator().getId();
            Integer userContextId = userContext.getUser().getId();

            if (surveyCreatorId.equals(userContextId)) {
                return true;
            }
        } catch (javax.persistence.NoResultException ex) {
        } catch (NullPointerException ex) {
        }

        return false;
    }

    public boolean isSurveyConfidential(String surveyLink) {
        try {
            return surveyDAO.getSurveyByLink(surveyLink).getConfidential();
        } catch (javax.persistence.NoResultException ex) {
        } catch (NullPointerException ex) {
        }

        return false;
    }

    public boolean isSurveyValid(String surveyLink) throws NoResultException {
        try {
            return surveyDAO.getSurveyByLink(surveyLink)
                    .getValidate()
                    .after(Calendar.getInstance().getTime());
        } catch (NullPointerException ex) {
        }

        return false;
    }
}
