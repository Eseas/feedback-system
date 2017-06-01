package lt.vu.feedback_system.businesslogic.spreadsheets.imports.excel;

import com.google.common.collect.Lists;
import lt.vu.feedback_system.businesslogic.spreadsheets.HelperValues;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.utils.CollectionUtils;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import lt.vu.feedback_system.utils.abstractions.Result;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


final class ExcelSurveySheetParser {

    private final static DataFormatter formatter = new DataFormatter();

    private ExcelSurveySheetParser() {}

    static Result<List<Question>> parseQuestions(final Result<Sheet> surveySheetResult) {
        final Result<List<Question>> parsedQuestions;
        if (surveySheetResult.isSuccess()) {
            final Sheet surveySheet = surveySheetResult.get();
            final List<Row> rows = ExcelSheetParserHelper.getUsableRows(Lists.newArrayList(surveySheet.rowIterator()));
            if (rows.size() >= 2) {
                if (surveyFirstRowIsValid(surveySheet)) {
                    List<Result<Question>> parsed = rows.subList(1, rows.size()).stream()
                            .map(ExcelSurveySheetParser::parseQuestion).collect(Collectors.toList());
                    Optional<Result<Question>> firstFailure = CollectionUtils.findFirst(parsed.stream(), Result::isFailure);
                    if (!firstFailure.isPresent()) {
                        final List<Question> successfullyParsed = parsed.stream().map(Result::get).collect(Collectors.toList());
                        final Map<Integer, Question> positionQuestionMap = successfullyParsed.stream()
                                .collect(Collectors.toMap(Question::getPosition, Function.identity(), (v1, v2) -> v1));
                        if (successfullyParsed.size() == positionQuestionMap.size()) parsedQuestions = Result.Success(successfullyParsed);
                        else parsedQuestions = Result.Failure("Survey sheet: There are duplicate question numbers");
                    } else parsedQuestions = Result.Failure(firstFailure.get().getFailureMsg());
                } else parsedQuestions = Result.Failure(String.format(
                    "Survey sheet: First four cells of the first row must be filled with the following values: %s, %s, %s and %s",
                        HelperValues.SurveyFirstRow.FirstColValue,
                        HelperValues.SurveyFirstRow.SecondColValue,
                        HelperValues.SurveyFirstRow.ThirdColValue,
                        HelperValues.SurveyFirstRow.FourthColValue
                ));
            } else parsedQuestions = Result.Failure("Survey sheet: spreadsheet has no questions");
        } else parsedQuestions = Result.Failure(surveySheetResult.getFailureMsg());
        return parsedQuestions;
    }

    private static Result<Question> parseQuestion(final Row row) {
        final Result<Question> parsedQuestion;
        if (ExcelSheetParserHelper.rowIsFilled(row, 3)) {
            final List<Cell> cells = Lists.newArrayList(row.cellIterator());
            int cellIndex = 0;
            int questionNumber = ParserWithDefaults.parseInt(formatter.formatCellValue(cells.get(cellIndex++)), -1);

            if (questionNumber > 0) {
                if (cells.size() > 4) {
                    cellIndex++;
                }

                final String questionTitle = formatter.formatCellValue(cells.get(cellIndex++));
                final String questionType = formatter.formatCellValue(cells.get(cellIndex++));
                final Result<List<String>> optionsResult = parseOptionList(cells);
                switch (questionType.toUpperCase()) {
                    case HelperValues.SpreadsheetQuestionTypes.Text:
                        if (optionsResult.isFailure()) {
                            final TextQuestion textQuestion = (TextQuestion) setCommonFields(
                                    new TextQuestion(), questionTitle, questionNumber);
                            parsedQuestion = Result.Success(textQuestion);
                        } else parsedQuestion = Result.Failure(String.format("Survey sheet: question of type '%s' cannot have any options", questionType));
                        break;
                    case HelperValues.SpreadsheetQuestionTypes.Radio:
                        if (optionsResult.isSuccess()) {
                            final RadioQuestion radioQuestion = (RadioQuestion) setCommonFields(
                                    new RadioQuestion(), questionTitle, questionNumber);
                            final List<RadioButton> radioButtons = optionsResult.get().stream().map(o -> {
                                final RadioButton radioButton = new RadioButton();
                                radioButton.setTitle(o);
                                radioButton.setQuestion(radioQuestion);
                                return radioButton;
                            }).collect(Collectors.toList());
                            radioQuestion.setRadioButtons(radioButtons);
                            parsedQuestion = Result.Success(radioQuestion);
                        } else parsedQuestion = Result.Failure(optionsResult.getFailureMsg());
                        break;
                    case HelperValues.SpreadsheetQuestionTypes.Checkbox:
                        if (optionsResult.isSuccess()) {
                            final CheckboxQuestion checkboxQuestion = (CheckboxQuestion) setCommonFields(
                                    new CheckboxQuestion(), questionTitle, questionNumber);
                            final List<Checkbox> checkboxes = optionsResult.get().stream().map(o -> {
                                final Checkbox checkbox = new Checkbox();
                                checkbox.setTitle(o);
                                checkbox.setQuestion(checkboxQuestion);
                                return checkbox;
                            }).collect(Collectors.toList());
                            checkboxQuestion.setCheckboxes(checkboxes);
                            parsedQuestion = Result.Success(checkboxQuestion);
                        } else parsedQuestion = Result.Failure(optionsResult.getFailureMsg());
                        break;
                    case HelperValues.SpreadsheetQuestionTypes.Slider:
                        if (optionsResult.isSuccess()) {
                            final SliderQuestion sliderQuestion = (SliderQuestion) setCommonFields(
                                    new SliderQuestion(), questionTitle, questionNumber);
                            final List<Integer> options = optionsResult.get().stream().map(o ->
                                    ParserWithDefaults.parseInt(o, Integer.MIN_VALUE)).collect(Collectors.toList());
                            if (options.size() == 2) {
                                int lowerBound = options.get(0);
                                int upperBound = options.get(1);
                                if (lowerBound != Integer.MIN_VALUE && upperBound != Integer.MAX_VALUE) {
                                    if (lowerBound < upperBound) {
                                        sliderQuestion.setLowerBound(lowerBound);
                                        sliderQuestion.setUpperBound(upperBound);
                                        parsedQuestion = Result.Success(sliderQuestion);
                                    } else parsedQuestion = Result.Failure("Survey sheet: lower bound has to be smaller than upper bound");
                                } else parsedQuestion = Result.Failure("Survey sheet: upper and lower bound have to be integral values");
                            } else parsedQuestion = Result.Failure(String.format("Survey sheet: question of type '%s' must have two values: upper and lower bound", questionType));

                        } else parsedQuestion = Result.Failure(optionsResult.getFailureMsg());
                        break;
                    default:
                        parsedQuestion = Result.Failure(String.format("Survey sheet: question type '%s' is not valid", questionType));
                }
            } else parsedQuestion = Result.Failure("Survey sheet: question numbers has to be integral values greater than 0");
        } else parsedQuestion = Result.Failure("Survey sheet: one or more rows are missing values");
        return parsedQuestion;
    }

    private static Result<List<String>> parseOptionList(List<Cell> cells) {
        final Result<List<String>> optionsResult;
        final String failureMsg = String.format("Survey sheet: %s cannot be empty", HelperValues.SurveyFirstRow.FourthColValue);
        int optionsStartAt = 4;

        if (cells.size() > 4) {
            optionsStartAt++;
        }

        if (cells.size() >= optionsStartAt) {
            final List<Cell> fullCells = cells.stream().skip(optionsStartAt - 1).filter(ExcelSheetParserHelper::cellIsFull).collect(Collectors.toList());
            if (fullCells.size() > 0) optionsResult = Result.Success(fullCells.stream().map(formatter::formatCellValue).collect(Collectors.toList()));
            else optionsResult = Result.Failure(failureMsg);
        } else optionsResult = Result.Failure(failureMsg);
        return optionsResult;
    }

    private static Question setCommonFields(Question q, String title, int position) {
        q.setTitle(title);
        q.setRequired(false);
        q.setPosition(position);
        return q;
    }

    private static boolean surveyFirstRowIsValid(final Sheet surveySheet) {
        final int firstRowNum = surveySheet.getFirstRowNum();
        final boolean isValid;
        if (firstRowNum == 0) {
            Row firstRow = surveySheet.getRow(firstRowNum);
            List<Cell> cells = firstRow != null ? Lists.newArrayList(firstRow) : new ArrayList<>();
            List<String> cellValues = cells.stream().map(formatter::formatCellValue).collect(Collectors.toList());
            isValid = cellValues.indexOf(HelperValues.SurveyFirstRow.FirstColValue) == 0
                    && ((cellValues.indexOf(HelperValues.SurveyFirstRow.SecondColValue) == 1
                    && cellValues.indexOf(HelperValues.SurveyFirstRow.ThirdColValue) == 2
                    && cellValues.indexOf(HelperValues.SurveyFirstRow.FourthColValue) == 3)
                    || (cellValues.indexOf(HelperValues.SurveyFirstRow.SecondColValue) == 2
                    && cellValues.indexOf(HelperValues.SurveyFirstRow.ThirdColValue) == 3
                    && cellValues.indexOf(HelperValues.SurveyFirstRow.FourthColValue) == 4));
        } else isValid = false;
        return isValid;
    }

    public static boolean surveyFirstRowValueCheck(final Sheet surveySheet, final String value, final int index) {
        final int firstRowNum = surveySheet.getFirstRowNum();
        boolean isValid = false;

        if (firstRowNum == 0) {
            Row firstRow = surveySheet.getRow(firstRowNum);
            List<Cell> cells = firstRow != null ? Lists.newArrayList(firstRow) : new ArrayList<>();
            List<String> cellValues = cells.stream().map(formatter::formatCellValue).collect(Collectors.toList());
            isValid = cellValues.indexOf(value) == index;
        }
        return isValid;
    }

}
