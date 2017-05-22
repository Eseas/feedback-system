package lt.vu.feedback_system.businesslogic.spreadsheets.exports.excel;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.entities.answers.Answer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.List;

public class ExcelAnswerSheetFiller {

    static void fillAnswerSheet(final Sheet sheet, final List<Answer> answers) {
        fillAnswerSheetFirstRow(sheet);
    }

    private static void fillAnswerSheetFirstRow(final Sheet sheet) {
        final Row firstRow = sheet.createRow(0);
        final List<String> firstRowValues = Lists.newArrayList(
                HelperValues.AnswerFirstRow.FirstColValue,
                HelperValues.AnswerFirstRow.SecondColValue,
                HelperValues.AnswerFirstRow.ThirdColValue
        );
        firstRowValues.forEach(v -> {
            final Cell cell = firstRow.createCell(firstRowValues.indexOf(v));
            cell.setCellValue(v);
        });
    }
}
