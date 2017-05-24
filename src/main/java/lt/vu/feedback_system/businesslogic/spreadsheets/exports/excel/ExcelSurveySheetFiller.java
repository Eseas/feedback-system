package lt.vu.feedback_system.businesslogic.spreadsheets.exports.excel;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.entities.questions.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.stream.Collectors;

final class ExcelSurveySheetFiller {

    static void fillSurveySheet(final Sheet sheet, final List<Question> questions) {
        fillSurveySheetFirstRow(sheet);
        questions.forEach(q -> {
            final Row row = sheet.createRow(questions.indexOf(q) + 1);
            switch (q.getType()) {
                case HelperValues.EntityQuestionTypes.Text:
                    fillTextQuestionRow(row, (TextQuestion) q);
                    break;
                case HelperValues.EntityQuestionTypes.Radio:
                    fillRadioQuestionRow(row, (RadioQuestion) q);
                    break;
                case HelperValues.EntityQuestionTypes.Checkbox:
                    fillCheckboxQuestionRow(row, (CheckboxQuestion) q);
                    break;
                case HelperValues.EntityQuestionTypes.Slider:
                    fillSliderQuestionRow(row, (SliderQuestion) q);
                    break;
                default:
                    break;
            }
        });
    }

    private static void fillSurveySheetFirstRow(final Sheet sheet) {
        final Row firstRow = sheet.createRow(0);
        final List<String> firstRowValues = Lists.newArrayList(
                HelperValues.SurveyFirstRow.FirstColValue,
                HelperValues.SurveyFirstRow.SecondColValue,
                HelperValues.SurveyFirstRow.ThirdColValue,
                HelperValues.SurveyFirstRow.FourthColValue
        );
        firstRowValues.forEach(v -> {
            final Cell cell = firstRow.createCell(firstRowValues.indexOf(v));
            cell.setCellValue(v);
        });
    }

    private static void fillTextQuestionRow(final Row row, final TextQuestion question) {
        fillQuestionRow(row, question);
    }

    private static void fillRadioQuestionRow(final Row row, final RadioQuestion question) {
        final int optionsStartAt = fillQuestionRow(row, question);
        fillRemainingColumns(row, optionsStartAt, question.getRadioButtons().stream().map(RadioButton::getTitle).collect(Collectors.toList()));
    }

    private static void fillCheckboxQuestionRow(final Row row, final CheckboxQuestion question) {
        final int optionsStartAt = fillQuestionRow(row, question);
        fillRemainingColumns(row, optionsStartAt, question.getCheckboxes().stream().map(Checkbox::getTitle).collect(Collectors.toList()));
    }

    private static void fillSliderQuestionRow(final Row row, final SliderQuestion question) {
        final int optionsStartAt = fillQuestionRow(row, question);
        fillRemainingColumns(row, optionsStartAt, Lists.newArrayList(question.getLowerBound().toString(), question.getUpperBound().toString()));
    }

    private static int fillQuestionRow(final Row row, final Question question) {
        row.createCell(0).setCellValue(row.getRowNum());
        row.createCell(1).setCellValue(question.getTitle());
        row.createCell(2).setCellValue(HelperValues.typesMap.get(question.getType()));
        return 3;
    }

    private static void fillRemainingColumns(final Row row, final int startAt, final List<String> values) {
        values.forEach(v -> row.createCell(startAt + values.indexOf(v)).setCellValue(v));
    }

}
