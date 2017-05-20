package lt.vu.feedback_system.usecases.surveys;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import lombok.Getter;
import lt.vu.feedback_system.entities.questions.Question;
import lt.vu.feedback_system.utils.Sorter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

@Named
@ManagedBean
public class SurveyChartController implements Serializable {
    @Inject
    SurveyReportController reportController;

    private PieChartModel pieModel;
    private BarChartModel barModel;
    @Getter
    public TagCloudModel cloudModel;

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

    public void createTagCloud(Question question) {

        List<String> answers = reportController.getUniqueQuestionTextAnswers(question);
        Map map = new HashMap();
        for(String answer:answers){
           map.put(answer,reportController.countTextAnswers(answer,question));
        }
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);

        //su situo list buna isrikiuota bet tada negraziai atrodo labai zemelapis
        //List<Map.Entry<String, Integer>> list = Sorter.sortMapByValue(map);
        cloudModel = new DefaultTagCloudModel();
        for (Map.Entry<String, Integer> entry : list) {
            cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(),entry.getValue()));
            System.out.println(entry.getValue());

        }

    }
    public TagCloudModel getModel() {
        return cloudModel;
    }

}