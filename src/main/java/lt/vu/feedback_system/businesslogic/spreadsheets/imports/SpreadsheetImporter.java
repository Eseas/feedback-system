package lt.vu.feedback_system.businesslogic.spreadsheets.imports;

import lt.vu.feedback_system.businesslogic.spreadsheets.SpreadsheetException;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import java.io.IOException;

public interface SpreadsheetImporter {

    void importSurvey(final Survey survey, UploadedFile file) throws SpreadsheetException, IOException;

    StreamedContent getImportExample();

}
