package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.chart.BarChartModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Entity
@Table(schema = "feedback", name = "checkbox_questions")
@NamedQueries({
        @NamedQuery(name = "CheckboxQuestion.findAll", query = "SELECT c FROM CheckboxQuestion c"),
        @NamedQuery(name = "CheckboxQuestion.findBySurveyId", query = "SELECT s FROM CheckboxQuestion s WHERE s.survey = :survey"),
        @NamedQuery(name = "CheckboxQuestion.findBySectionId", query = "SELECT s FROM CheckboxQuestion s WHERE s.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "title"})
@ToString(of = {"id", "title", "position", "required"})
public class CheckboxQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "CheckboxQuestion";

    @Size(max = 200)
    @Column(name = "title")
    private String title;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Checkbox> checkboxes = new ArrayList<>();


    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();

    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne
    private Section section;

    @Transient
    public Future<BarChartModel> model = null;
}

