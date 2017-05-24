package lt.vu.feedback_system.usecases.spreadsheets;

import lt.vu.feedback_system.businesslogic.spreadsheets.exports.SpreadsheetExporter;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.StreamedContent;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.IOException;

@Model
public class ExportController {

    private SpreadsheetExporter spreadsheetExporter;

    protected ExportController() {
    }

    @Inject
    public ExportController(SpreadsheetExporter spreadsheetExporter) {
        this.spreadsheetExporter = spreadsheetExporter;
    }

    public StreamedContent getSpreadsheet(final Survey survey) throws IOException {
        return spreadsheetExporter.exportSurvey(survey);
    }

}
