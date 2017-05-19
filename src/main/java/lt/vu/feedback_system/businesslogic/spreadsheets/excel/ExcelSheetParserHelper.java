package lt.vu.feedback_system.businesslogic.spreadsheets.excel;


import com.google.common.collect.Streams;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.stream.Collectors;

final class ExcelSheetParserHelper {

    private final static DataFormatter formatter = new DataFormatter();

    private ExcelSheetParserHelper() {}

    static List<Row> filterOutEmptyRows(List<Row> rows) {
        return rows.stream().filter(r -> !rowIsEmpty(r)).collect(Collectors.toList());
    }

    /**
     * Row is empty if all its cells contain only whitespaces and non-visible characters
     * @param row row to check if it's empty.
     * @return
     */
    static boolean rowIsEmpty(Row row) {
        return Streams.stream(row.cellIterator()).allMatch(c -> formatter.formatCellValue(c).replaceAll("\\s+","").isEmpty());
    }

}
