package lt.vu.feedback_system.businesslogic.spreadsheets.exports;

import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.StreamedContent;

import java.io.IOException;

public interface SpreadsheetExporter {

    StreamedContent exportSurvey(final Survey survey) throws IOException;

}
