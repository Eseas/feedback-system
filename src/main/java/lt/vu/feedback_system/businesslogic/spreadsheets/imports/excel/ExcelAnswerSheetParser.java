package lt.vu.feedback_system.businesslogic.spreadsheets.imports.excel;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.entities.answers.*;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.utils.CollectionUtils;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import lt.vu.feedback_system.utils.abstractions.Result;
import lt.vu.feedback_system.utils.abstractions.Tuple;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

final public class ExcelAnswerSheetParser {

    private final static DataFormatter formatter = new DataFormatter();

    private ExcelAnswerSheetParser() {}

    static Result<Map<Integer, List<Answer>>> parseAnswers(final Result<Sheet> answerSheetResult, final Result<List<Question>> questionsResult) {
        final Result<Map<Integer, List<Answer>>> parsedAnswers;
        if (answerSheetResult.isSuccess()) {
            if (questionsResult.isSuccess()) {
                final Sheet answerSheet = answerSheetResult.get();
                final List<Row> rows = ExcelSheetParserHelper.getUsableRows(Lists.newArrayList(answerSheet.rowIterator()));
                if (rows.size() >= 2) {
                    if (answerFirstRowIsValid(answerSheet)) {
                        List<Result<Tuple<Integer, Answer>>> parsed = rows.subList(1, rows.size()).stream()
                                .map(r -> ExcelAnswerSheetParser.parseAnswer(r, questionsResult.get())).collect(Collectors.toList());
                        Optional<Result<Tuple<Integer, Answer>>> firstFailure = CollectionUtils.findFirst(parsed.stream(), Result::isFailure);
                        if (!firstFailure.isPresent()) {
                            final Map<Integer, List<Answer>> surveyIdAnswers = new HashMap<>();
                            parsed.forEach(e -> {
                                final Integer key = e.get().first();
                                final Answer value = e.get().second();
                                if (!surveyIdAnswers.containsKey(key)) surveyIdAnswers.put(key, new ArrayList<>());
                                surveyIdAnswers.get(key).add(value);
                            });
                            parsedAnswers = Result.Success(surveyIdAnswers);
                        } else parsedAnswers = Result.Failure(firstFailure.get().getFailureMsg());
                    } else parsedAnswers = Result.Failure(String.format(
                            "Answer sheet: First three cells of the first row must be filled with the following values: %s, %s and %s",
                            HelperValues.AnswerFirstRow.FirstColValue,
                            HelperValues.AnswerFirstRow.FirstColValue,
                            HelperValues.AnswerFirstRow.SecondColValue,
                            HelperValues.AnswerFirstRow.ThirdColValue
                    ));
                } else parsedAnswers = Result.Failure("Answer sheet: spreadsheet has no answers");
            } else parsedAnswers = Result.Failure(questionsResult.getFailureMsg());
        } else parsedAnswers = Result.Failure(answerSheetResult.getFailureMsg());
        return parsedAnswers;
    }

    private static Result<Tuple<Integer, Answer>> parseAnswer(final Row row, final List<Question> questions) {
        final Result<Tuple<Integer, Answer>> parsedAnswer;
        if (ExcelSheetParserHelper.rowIsFilled(row, 3)) {
            final List<Cell> cells = Lists.newArrayList(row.cellIterator());
            final int surveyId = ParserWithDefaults.parseInt(formatter.formatCellValue(cells.get(0)));
            final int questionNumber = ParserWithDefaults.parseInt(formatter.formatCellValue(cells.get(1)));
            if (surveyId > 0 && questionNumber > 0) {
                final Optional<Question> notSafe = CollectionUtils.findFirst(questions.stream(), q -> q.getPosition() == questionNumber);
                if (notSafe.isPresent()) {
                    final Question question = notSafe.get();
                    final List<String> answerValues = answerValues(cells);
                    final String questionType = question.getType();
                    final String excelQuestionType = HelperValues.typesMap.getOrDefault(questionType, "UNMATCHED_TYPE");
                    switch (questionType) {
                        case HelperValues.EntityQuestionTypes.Text:
                            if (answerValues.size() == 1) {
                                final TextAnswer textAnswer = new TextAnswer();
                                textAnswer.setQuestion((TextQuestion) question);
                                textAnswer.setValue(answerValues.get(0));
                                parsedAnswer = Result.Success(new Tuple<>(surveyId, textAnswer));
                            } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s can only have one answer", excelQuestionType));
                            break;
                        case HelperValues.EntityQuestionTypes.Radio:
                            if (answerValues.size() == 1) {
                                final int optionId = ParserWithDefaults.parseInt(answerValues.get(0));
                                if (optionId > 0) {
                                    final RadioAnswer radioAnswer = new RadioAnswer();
                                    final RadioQuestion radioQuestion = (RadioQuestion) question;
                                    final List<RadioButton> radioButtons = radioQuestion.getRadioButtons();
                                    if (radioButtons.size() >= optionId) {
                                        radioAnswer.setQuestion(radioQuestion);
                                        radioAnswer.setRadioButton(radioButtons.get(optionId - 1));
                                        parsedAnswer = Result.Success(new Tuple<>(surveyId, radioAnswer));
                                    } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s answer with option id %d does not exist", excelQuestionType, optionId));
                                } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s answer must be an integral value greater than zero", excelQuestionType));
                            } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s can only have one answer", excelQuestionType));
                            break;
                        case HelperValues.EntityQuestionTypes.Checkbox:
                            final List<Integer> optionIds = answerValues.stream().map(ParserWithDefaults::parseInt).collect(Collectors.toList());
                            if (optionIds.stream().allMatch(i -> i > 0)) {
                                final CheckboxAnswer checkboxAnswer = new CheckboxAnswer();
                                final CheckboxQuestion checkboxQuestion = (CheckboxQuestion) question;
                                final List<Checkbox> checkboxes = checkboxQuestion.getCheckboxes();
                                final int maxOptionId = Collections.max(optionIds);
                                if (checkboxes.size() >= maxOptionId) {
                                    final List<SelectedCheckbox> selectedCheckboxes = optionIds.stream().map(i -> {
                                        final SelectedCheckbox selected = new SelectedCheckbox();
                                        selected.setCheckbox(checkboxes.get(i - 1));
                                        selected.setCheckboxAnswer(checkboxAnswer);
                                        return selected;
                                    }).collect(Collectors.toList());
                                    checkboxAnswer.setSelectedCheckboxes(selectedCheckboxes);
                                    checkboxAnswer.setQuestion(checkboxQuestion);
                                    parsedAnswer = Result.Success(new Tuple<>(surveyId, checkboxAnswer));
                                } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s answer with option id %d does not exist", excelQuestionType, maxOptionId));
                            } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s must have a list of integral values greater than zero as answers", excelQuestionType));
                            break;
                        case HelperValues.EntityQuestionTypes.Slider:
                            if (answerValues.size() == 1) {
                                final int sliderValue = ParserWithDefaults.parseInt(answerValues.get(0), Integer.MIN_VALUE);
                                if (sliderValue != Integer.MIN_VALUE) {
                                    final SliderQuestion sliderQuestion = (SliderQuestion) question;
                                    if (sliderQuestion.getLowerBound() <= sliderValue && sliderValue <= sliderQuestion.getUpperBound()) {
                                        final SliderAnswer sliderAnswer = new SliderAnswer();
                                        sliderAnswer.setQuestion(sliderQuestion);
                                        sliderAnswer.setValue(sliderValue);
                                        parsedAnswer = Result.Success(new Tuple<>(surveyId, sliderAnswer));
                                    } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s answer value is out of bounds", excelQuestionType));
                                } else  parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s answer must be an integral value", excelQuestionType));
                            } else parsedAnswer = Result.Failure(String.format("Answer sheet: question of type %s can only have one answer value", excelQuestionType));
                            break;
                        default:
                            parsedAnswer = Result.Failure(String.format("Answer sheet: failed to detect answer with question type '%s'", questionType));
                    }
                } else parsedAnswer = Result.Failure(String.format("Answer sheet: question with number %d is not found", questionNumber));
            } else parsedAnswer = Result.Failure("Answer sheet: answer ids and question numbers has to be integral values greater than 0");
        } else parsedAnswer = Result.Failure("Answer sheet: one or more rows are missing values");
        return parsedAnswer;
    }

    private static List<String> answerValues(List<Cell> cells) {
        final int answersStartAt = 3;
        return cells.stream().skip(answersStartAt -1).filter(ExcelSheetParserHelper::cellIsFull)
                .map(formatter::formatCellValue).collect(Collectors.toList());
    }

    private static boolean answerFirstRowIsValid(final Sheet answerSheet) {
        final int firstRowNum = answerSheet.getFirstRowNum();
        final boolean isValid;
        if (firstRowNum == 0) {
            Row firstRow = answerSheet.getRow(firstRowNum);
            List<Cell> cells = firstRow != null ? Lists.newArrayList(firstRow) : new ArrayList<>();
            List<String> cellValues = cells.stream().map(formatter::formatCellValue).collect(Collectors.toList());
            isValid = cellValues.indexOf(HelperValues.AnswerFirstRow.FirstColValue) == 0
                    && cellValues.indexOf(HelperValues.AnswerFirstRow.SecondColValue) == 1
                    && cellValues.indexOf(HelperValues.AnswerFirstRow.ThirdColValue) == 2;
        } else isValid = false;
        return isValid;
    }

}
