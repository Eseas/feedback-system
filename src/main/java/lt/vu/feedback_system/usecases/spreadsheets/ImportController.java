package lt.vu.feedback_system.usecases.spreadsheets;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetException;
import lt.vu.feedback_system.businesslogic.spreadsheets.imports.SpreadsheetImporter;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.HexStringGen;
import org.primefaces.model.UploadedFile;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Slf4j
@Model
public class ImportController {

    @Getter
    @Setter
    private UploadedFile file;

    @Getter
    private Survey survey;

    private User creator;

    private SpreadsheetImporter importer;

    private FacesContext context;

    protected ImportController() {}

    @Inject
    public ImportController(SpreadsheetImporter importer, UserContext userContext) {
        this.context = FacesContext.getCurrentInstance();
        this.importer = importer;
        this.survey = new Survey(true);
        this.creator = userContext.getUser();
    }

    public void upload() {
        FacesMessage msg;
        if (creator != null) {
            survey.setCreator(creator);
            survey.setLink(HexStringGen.getHexString(10));
            try {
                importer.importSurvey(survey, file);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Survey is successfully imported");
            } catch (Throwable e) {
                final Throwable cause = e.getCause();
                if (cause instanceof SpreadsheetException) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", cause.getMessage());
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to import survey. If error persists contact system administrator");
                    log.error(String.format("Failed to import survey: %s", cause.getMessage()));
                }
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "You have to be logged in to import surveys");
        }
        context.addMessage(null, msg);
    }

}
