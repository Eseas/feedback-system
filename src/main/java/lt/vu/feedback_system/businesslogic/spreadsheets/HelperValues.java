package lt.vu.feedback_system.businesslogic.spreadsheets;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public final class HelperValues {

    private HelperValues() {}

    public final static class ContentTypes {
        // https://www.sitepoint.com/web-foundations/mime-types-summary-list/
        public static final String Xls = "application/vnd.ms-excel";
        public static final String Xlsx = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        private ContentTypes() {}
    }

    public final static class SurveyFirstRow {
        public static final String FirstColValue = "$questionNumber";
        public static final String SecondColValue = "$question";
        public static final String ThirdColValue = "$questionType";
        public static final String FourthColValue = "$optionsList";
        private SurveyFirstRow() {}
    }

    public final static class AnswerFirstRow {
        public static final String FirstColValue = "$answerID";
        public static final String SecondColValue = "$questionNumber";
        public static final String ThirdColValue = "$answer";
        private AnswerFirstRow() {}
    }

    public final static class SpreadsheetQuestionTypes {
        public static final String Text = "TEXT";
        public static final String Radio = "MULTIPLECHOICE";
        public static final String Checkbox = "CHECKBOX";
        public static final String Slider = "SCALE";
        private SpreadsheetQuestionTypes() {}
    }

    public final static class EntityQuestionTypes {
        public static final String Text = "TextQuestion";
        public static final String Radio = "RadioQuestion";
        public static final String Checkbox = "CheckboxQuestion";
        public static final String Slider = "SliderQuestion";
        private EntityQuestionTypes() {}
    }

    /**
     * map EntityQuestionTypes to SpreadsheetQuestionTypes
     */
    public static final Map<String, String> typesMap = ImmutableMap.of(
        EntityQuestionTypes.Text, SpreadsheetQuestionTypes.Text,
        EntityQuestionTypes.Radio, SpreadsheetQuestionTypes.Radio,
        EntityQuestionTypes.Checkbox, SpreadsheetQuestionTypes.Checkbox,
        EntityQuestionTypes.Slider, SpreadsheetQuestionTypes.Slider
    );

}
