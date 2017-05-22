package lt.vu.feedback_system.usecases.surveys;

import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.SelectedCheckboxDAO;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.utils.Sorter;
import org.primefaces.model.chart.*;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ManagedBean
public class SurveyChartController implements Serializable {
    @Inject
    SurveyReportController reportController;
    @Inject
    private AnswerDAO answerDAO;

    @Inject
    private SelectedCheckboxDAO selectedCheckboxDAO;

    public PieChartModel createPieModel(Question question) {
        List<RadioButton> radioButtons = ((RadioQuestion)question).getRadioButtons();
        PieChartModel pieModel = new PieChartModel();
        for(RadioButton radioButton : radioButtons){
            int count = answerDAO.getRadioAnswersByRadioButton(radioButton).size();
            pieModel.set(radioButton.getTitle(), count) ;
        }
        pieModel.setTitle(question.getTitle());
        pieModel.setLegendPosition("e");
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
        return pieModel;
    }

    public BarChartModel createBarModel(Question question) {
        int maxAxis = 0;

        BarChartModel barModel = new BarChartModel();
        barModel.setShowPointLabels(true);
        for(Checkbox checkbox : ((CheckboxQuestion)question).getCheckboxes()) {
            int count = selectedCheckboxDAO.getSelectedCheckboxesByCheckbox(checkbox).size();
            if (count > maxAxis){
                maxAxis = count;
            }

            ChartSeries series = new ChartSeries();
            series.setLabel(checkbox.getTitle());
            series.set(" ", count);
            barModel.addSeries(series);
        }

        barModel.setTitle(question.getTitle());
        barModel.setLegendPosition("ne");
        barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);

        maxAxis = maxAxis*3/2;
        yAxis.setMax(maxAxis);
        return barModel;
    }

    public TagCloudModel createTagCloud(Question question) {

        List<String> answers = reportController.getUniqueQuestionTextAnswers(question);
        Map map = new HashMap();
        for(String answer:answers){
            map.put(answer,reportController.countTextAnswers(answer,question));
        }
        List<Map.Entry<String, Integer>> list = Sorter.sortMapByValue(map);
        TagCloudModel cloudModel = new DefaultTagCloudModel();
        int index= 5;
        for (Map.Entry<String, Integer> entry : list) {
            if(index < 1){break;}
            cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(),index));
            index--;
        }
        return cloudModel;
    }

}