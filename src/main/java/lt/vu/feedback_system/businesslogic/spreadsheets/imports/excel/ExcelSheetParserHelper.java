package lt.vu.feedback_system.businesslogic.spreadsheets.imports.excel;

import com.google.common.collect.Streams;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


final class ExcelSheetParserHelper {

    private final static DataFormatter formatter = new DataFormatter();

    private ExcelSheetParserHelper() {}

    static List<Row> getUsableRows(final List<Row> rows) {
        final List<Row> usableRows = new ArrayList<>();
        final Iterator<Row> iterator = rows.iterator();
        int prevRowNumber = 0;
        boolean continueLoop = true;
        while (iterator.hasNext() && continueLoop) {
            Row row = iterator.next();
            if (row.getRowNum() - prevRowNumber > 1
                || row.getCell(0) == null
                || row.getCell(0).getCellTypeEnum().equals(CellType.BLANK)) {
                continueLoop = false;
            } else {
                prevRowNumber = row.getRowNum();
                usableRows.add(row);
            }
        }
        return usableRows;
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
