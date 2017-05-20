package lt.vu.feedback_system.usecases.spreadsheets;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.businesslogic.spreadsheets.excel.ExcelImporter;
import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetException;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.entities.surveys.Survey;
import lt.vu.feedback_system.utils.HexStringGen;
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
        this.survey.setLink(HexStringGen.getHexString(10));
        System.out.println("user is: " + userContext.getUser());
    }

    // TODO: check if survey fields are filled also if user is logged in
    public void upload() throws SpreadsheetException, IOException {
        System.out.println(survey);
        try {
            importer.importSurvey(survey, file);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            System.out.println(e.getCause().getMessage());
        }
        printInfo();
    }

    private void printInfo() {
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
        System.out.println(file.getFileName());
    }

}
