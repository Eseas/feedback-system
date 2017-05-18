package lt.vu.feedback_system.usecases.spreadsheets;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.businesslogic.spreadsheets.ExcelImporter;
import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetImportException;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.UploadedFile;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;

@Slf4j
@Model
public class ImportController {

    @Getter
    @Setter
    private UploadedFile file;

    @Getter
    private Survey survey;

    private ExcelImporter importer;

    private UserContext userContext;

    private FacesContext facesContext;

    protected ImportController() {}

    @Inject
    public ImportController(ExcelImporter importer, UserContext userContext) {
        this.facesContext = FacesContext.getCurrentInstance();
        this.importer = importer;
        this.survey = new Survey();
        this.survey.setCreator(userContext.getUser());
        System.out.println("user is: " + userContext.getUser());
    }

    public void upload() throws SpreadsheetImportException, IOException {
        System.out.println(survey);
        importer.importSurvey(survey, file);
        printInfo();
    }

    private void printInfo() {
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
        System.out.println(file.getFileName());
    }

}
