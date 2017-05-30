package lt.vu.feedback_system.businesslogic.reports;

import lt.vu.feedback_system.dao.AsyncAnswerDAO;
import lt.vu.feedback_system.dao.AsyncSelectedCheckboxDAO;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.questions.*;
import lt.vu.feedback_system.usecases.surveys.SurveyReportController;
import org.apache.deltaspike.core.api.future.Futureable;
import org.primefaces.model.chart.*;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.ejb.AsyncResult;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ChartLogic implements Serializable {
    @Inject
    SurveyReportController reportController;

    @Inject
    private AsyncAnswerDAO asyncAnswerDAO;

    @Inject
    private AsyncSelectedCheckboxDAO asyncSelectedCheckboxDAO;

    @Futureable
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Future<PieChartModel> createPieModel(RadioQuestion radioQuestion) {
        List<RadioButton> radioButtons = radioQuestion.getRadioButtons();
        PieChartModel pieModel = new PieChartModel();
        for(RadioButton radioButton : radioButtons){
            int count = asyncAnswerDAO.getRadioAnswersByRadioButton(radioButton).size();
            pieModel.set(radioButton.getTitle(), count) ;
        }
        pieModel.setTitle(radioQuestion.getTitle());
        pieModel.setLegendPosition("e");
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(pieModel);
    }


    @Futureable
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Future<BarChartModel> createBarModel(CheckboxQuestion checkboxQuestion) {
        int maxAxis = 0;

        BarChartModel barModel = new BarChartModel();
        barModel.setShowPointLabels(true);
        for (Checkbox checkbox : checkboxQuestion.getCheckboxes()) {
            int count = asyncSelectedCheckboxDAO.getSelectedCheckboxesByCheckbox(checkbox).size();
            if (count > maxAxis){
                maxAxis = count;
            }

            ChartSeries series = new ChartSeries();
            series.setLabel(checkbox.getTitle());
            series.set(" ", count);
            barModel.addSeries(series);
        }

        barModel.setTitle(checkboxQuestion.getTitle());
        barModel.setLegendPosition("ne");
        barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);

        maxAxis = maxAxis*3/2;
        yAxis.setMax(maxAxis);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(barModel);
    }

    @Futureable
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Future<TagCloudModel> createTagCloud(Question question) {

        TagCloudModel cloudModel = new DefaultTagCloudModel();

        List<TextAnswer> answers = asyncAnswerDAO.getAllTextAnswersByQuestionId(question.getId());
        List<String> allWordsForTagCloud = new ArrayList<>();

        for (Answer answer : answers) {
            List<String> splittedValues = new ArrayList<String>(Arrays.asList(((TextAnswer) answer).getValue().split(" ")));
            splittedValues.removeIf(s -> s.length() < 4);
            allWordsForTagCloud.addAll(splittedValues);
        }

        Map<String, Long> counts = allWordsForTagCloud.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        if (counts.size() == 0) {
            cloudModel.addTag(new DefaultTagCloudItem("Only greater than 3 symbol words are included in the TagCloud", 1));

            return new AsyncResult<>(cloudModel);
        }


        Map.Entry<String, Long> maxEntry = null;
        for (Map.Entry<String, Long> entry : counts.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        Map.Entry<String, Long> minEntry = null;
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            if (minEntry == null || minEntry.getValue() > entry.getValue()) {
                minEntry = entry;
            }
        }

        Integer smallestFont = 1;
        Integer largestFont = 5;

        Long largestCount = maxEntry.getValue();
        Long smallestCount = maxEntry == minEntry ? 0L : minEntry.getValue();



        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            // TagCloud formula: https://stackoverflow.com/a/18793324/4726792
            Long font = (
                    ((entry.getValue() - smallestCount) * (largestFont - smallestFont))
                            /
                            (largestCount - smallestCount)
            ) + smallestFont;

            cloudModel.addTag(new DefaultTagCloudItem(entry.getKey(), Math.toIntExact(font)));
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(cloudModel);
    }

}