package lt.vu.feedback_system.businesslogic.spreadsheets.excel;

import com.google.common.collect.Streams;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import java.util.List;
import java.util.stream.Collectors;


final class ExcelSheetParserHelper {

    private final static DataFormatter formatter = new DataFormatter();

    private ExcelSheetParserHelper() {}

    static List<Row> filterOutEmptyRows(final List<Row> rows) {
        return rows.stream().filter(r -> !rowIsEmpty(r)).collect(Collectors.toList());
    }

    /**
     * Row is empty if all its cells are empty
     * @param row row to check if it's empty
     * @return
     */
    static boolean rowIsEmpty(final Row row) {
        return Streams.stream(row.cellIterator()).allMatch(ExcelSheetParserHelper::cellIsEmpty);
    }

    /**
     * Cell is empty if it contains only whitespaces or other non-visible characters
     * @param cell cell to check if it's empty
     * @return
     */
    static boolean cellIsEmpty(final Cell cell) {
        return formatter.formatCellValue(cell).replaceAll("\\s+","").isEmpty();
    }

    static boolean cellIsFull(final Cell cell) {
        return !cellIsEmpty(cell);
    }

    /**
     * Checks if row contains at least minCellsFilled number of non empty cells
     * @param row row to check if it's full
     * @param minCellsFilled number of cells required to be filled
     * @return
     */
    static boolean rowIsFilled(Row row, int minCellsFilled) {
        return row.getPhysicalNumberOfCells() >= minCellsFilled
            && Streams.stream(row.cellIterator()).limit(minCellsFilled).allMatch(ExcelSheetParserHelper::cellIsFull);
    }

}
