package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.chart.PieChartModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Entity
@Table(schema = "feedback", name = "radio_questions")
@NamedQueries({
        @NamedQuery(name = "RadioQuestion.findAll", query = "SELECT c FROM RadioQuestion c"),
        @NamedQuery(name = "RadioQuestion.findBySurveyId", query = "SELECT s FROM RadioQuestion s WHERE s.survey = :survey"),
        @NamedQuery(name = "RadioQuestion.findBySectionId", query = "SELECT s FROM RadioQuestion s WHERE s.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "title"})
@ToString(of = {"id", "title", "position", "required"})
public class RadioQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "RadioQuestion";

    @Size(max = 200)
    @Column(name = "title")
    private String title;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RadioButton> radioButtons = new ArrayList<>();

    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne
    private Section section;

    @OneToMany(mappedBy = "question")
    private List<RadioAnswer> radioAnswers = new ArrayList<>();

    public List<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public Boolean getRequired() {
        return required;
    }

    @Transient
    public Future<PieChartModel> model = null;
}

