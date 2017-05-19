package lt.vu.feedback_system.businesslogic.spreadsheets;


public final class ParsingHelperValues {

    private ParsingHelperValues() {}

    public final static class SurveyFirstRow {
        public static final String FirstColValue = "$questionNumber";
        public static final String SecondColValue = "$question";
        public static final String ThirdColValue = "$questionType";
        public static final String FourthColValue = "$optionsList";
        private SurveyFirstRow() {}
    }

    public final static class QuestionTypes {
        public static final String Text = "TEXT";
        public static final String Radio = "MULTIPLECHOICE";
        public static final String Checkbox = "CHECKBOX";
        public static final String Slider = "SCALE";
        private QuestionTypes() {}
    }

}
