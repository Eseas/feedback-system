package lt.vu.feedback_system.usecases.surveys;

import lt.vu.feedback_system.dao.AnswerDAO;
import lt.vu.feedback_system.dao.SelectedCheckboxDAO;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.questions.*;
import org.primefaces.model.chart.*;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
        List<TextAnswer> answers = answerDAO.getAllTextAnswersByQuestionId(question.getId());
        List<String> result = new ArrayList<>();
        for (Answer answer : answers) {
            List<String> splittedValue = new ArrayList<String>(Arrays.asList(((TextAnswer)answer).getValue().split(" ")));
            result.addAll(splittedValue);
        }

        Map<String, Long> counts = result.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Map.Entry<String, Long> maxEntry = null;
        for (Map.Entry<String, Long> entry : counts.entrySet())
        {
            if(entry.getKey().length()>=4) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
        }
        TagCloudModel cloudModel = new DefaultTagCloudModel();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            if(entry.getKey().length()>= 4) {
                if (entry.getValue() <= maxEntry.getValue()/5) {
                    cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(), 1));
                } else if (entry.getValue() <= maxEntry.getValue()/5*2) {
                    cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(), 2));
                } else if (entry.getValue() <= maxEntry.getValue()/5*3) {
                    cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(), 3));
                } else if (entry.getValue() <= maxEntry.getValue()/5*4) {
                    cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(), 4));
                } else {
                    cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(), 5));
                }
            }
        }
        return cloudModel;
    }

}