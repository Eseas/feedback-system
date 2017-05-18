package lt.vu.feedback_system.businesslogic.spreadsheets;


final class ExcelImporterHelper {

    private ExcelImporterHelper() {}

    final static class SurveyFirstRow {
        static final String FirstColValue = "$questionNumber";
        static final String SecondColValue = "$question";
        static final String ThirdColValue = "$questionType";
        static final String FourthColValue = "$optionsList";
        // TODO: get rid of this
        static final String FirstColValidationErrorMsg = String.format(
            "First four cells of the first row in the Survey sheet must be filled with the following values: %s, %s, %s and %s",
                FirstColValue, SecondColValue, ThirdColValue, FourthColValue);
        private SurveyFirstRow() {}
    }

    final static class QuestionTypes {
        static final String Text = "TEXT";
        static final String Radio = "MULTIPLECHOICE";
        static final String Checkbox = "CHECKBOX";
        static final String Slider = "SCALE";
        private QuestionTypes() {}
    }

}
