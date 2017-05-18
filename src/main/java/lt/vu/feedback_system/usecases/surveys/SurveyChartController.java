package lt.vu.feedback_system.usecases.surveys;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import lt.vu.feedback_system.entities.questions.Question;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
@Named
@ManagedBean
public class SurveyChartController implements Serializable {
    @Inject
    SurveyReportController reportController;

    private PieChartModel pieModel;
    private BarChartModel barModel;


    public PieChartModel createPieModel(Question question) {
        List<String> answers = reportController.getUniqueQuestionCheckboxAnswers(question);
        pieModel = new PieChartModel();
        for(String answer : answers){
            int value = reportController.countCheckBoxAnswers(answer,question);
          pieModel.set(answer,value) ;
        }
        pieModel.setTitle(question.getTitle());
        pieModel.setLegendPosition("e");
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
        return pieModel;
    }

private BarChartModel initBarModel(Question question) {
    BarChartModel model = new BarChartModel();
    List<String> answers = reportController.getUniqueQuestionRadioAnswers(question);
    for(String answer : answers){
        int value = reportController.countRadioAnswers(answer,question);
        ChartSeries series = new ChartSeries();
        series.setLabel(answer);
        series.set("Answer Option",value);
        model.addSeries(series);
    }

    return model;
}
    public BarChartModel createBarModel(Question question) {
        barModel = initBarModel(question);
        barModel.setTitle(question.getTitle());
        barModel.setLegendPosition("ne");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        // setting yAxis based on max option answer count
        List<String> answers = reportController.getUniqueQuestionRadioAnswers(question);
        int maxAxis = 0;
        for(String answer : answers) {
        if (reportController.countRadioAnswers(answer,question)> maxAxis){
            maxAxis = reportController.countRadioAnswers(answer,question);
        }
        }
        maxAxis = maxAxis*3/2;
        yAxis.setMax(maxAxis);
        return barModel;
    }
}