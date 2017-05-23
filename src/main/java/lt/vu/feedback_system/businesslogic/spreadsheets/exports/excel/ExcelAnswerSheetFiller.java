package lt.vu.feedback_system.businesslogic.spreadsheets.exports.excel;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class ExcelAnswerSheetFiller {

    static void fillAnswerSheet(final Sheet sheet, final Map<Integer, List<Answer>> groupedAnswers, final List<Question> questions) {
        fillAnswerSheetFirstRow(sheet);
        final List<Integer> surveyIds = Lists.newArrayList(groupedAnswers.keySet());
        int currentRowNumber = 1;
        for (int id : surveyIds) {
            final List<Answer> answers = groupedAnswers.get(id);
            for (Answer a : answers) {
                final Question q = a.getQuestion();
                final Row row = sheet.createRow(currentRowNumber++);
                switch (q.getType()) {
                    case HelperValues.EntityQuestionTypes.Text:
                        fillTextAnswerRow(row, id + 1, (TextAnswer) a,questions.indexOf(q) + 1,  (TextQuestion) q);
                        break;
                    case HelperValues.EntityQuestionTypes.Radio:
                        fillRadioAnswerRow(row, id + 1, (RadioAnswer) a,questions.indexOf(q) + 1,  (RadioQuestion) q);
                        break;
                    case HelperValues.EntityQuestionTypes.Checkbox:
                        fillCheckboxAnswerRow(row, id + 1, (CheckboxAnswer) a,questions.indexOf(q) + 1,  (CheckboxQuestion) q);
                        break;
                    case HelperValues.EntityQuestionTypes.Slider:
                        fillSliderAnswerRow(row, id + 1, (SliderAnswer) a,questions.indexOf(q) + 1,  (SliderQuestion) q);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void fillTextAnswerRow(final Row row, final int surveyId, final TextAnswer answer, final int questionNumber, final TextQuestion question) {
        final int optionsStartAt = fillAnswerRow(row, surveyId, questionNumber);
        row.createCell(optionsStartAt).setCellValue(answer.getValue());
    }

    private static void fillRadioAnswerRow(final Row row, final int surveyId, final RadioAnswer answer, final int questionNumber, final RadioQuestion question) {
        final int optionsStartAt = fillAnswerRow(row, surveyId, questionNumber);
        final List<Integer> answerIds = Lists.newArrayList(question.getRadioButtons().indexOf(answer.getRadioButton()) + 1);
        fillRemainingColumns(row, optionsStartAt, answerIds);
    }

    private static void fillCheckboxAnswerRow(final Row row, final int surveyId, final CheckboxAnswer answer, final int questionNumber, final CheckboxQuestion question) {
        final int optionsStartAt = fillAnswerRow(row, surveyId, questionNumber);
        final List<Checkbox> checkboxes = question.getCheckboxes();
        final List<Integer> answerIds = answer.getSelectedCheckboxes().stream().map(s -> checkboxes.indexOf(s.getCheckbox()) + 1).collect(Collectors.toList());
        fillRemainingColumns(row, optionsStartAt, answerIds);
    }

    private static void fillSliderAnswerRow(final Row row, final int surveyId, final SliderAnswer answer, final int questionNumber, final SliderQuestion question) {
        final int optionsStartAt = fillAnswerRow(row, surveyId, questionNumber);
        final List<Integer> answerIds = Lists.newArrayList(answer.getValue());
        fillRemainingColumns(row, optionsStartAt, answerIds);
    }

    private static int fillAnswerRow(final Row row, final int surveyId, final int questionNumber) {
        row.createCell(0).setCellValue(surveyId);
        row.createCell(1).setCellValue(questionNumber);
        return 2;
    }

    private static void fillRemainingColumns(final Row row, final int startAt, final List<Integer> values) {
        values.forEach(v -> row.createCell(startAt + values.indexOf(v)).setCellValue(v));
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
