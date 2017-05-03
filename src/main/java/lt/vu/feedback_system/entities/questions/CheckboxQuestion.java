package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "checkbox_questions")
@NamedQueries({
        @NamedQuery(name = "CheckboxQuestion.findAll", query = "SELECT c FROM CheckboxQuestion c"),
        @NamedQuery(name = "CheckboxQuestion.findBySurveyId", query = "SELECT s FROM CheckboxQuestion s WHERE s.survey = :survey"),
        @NamedQuery(name = "CheckboxQuestion.findBySectionId", query = "SELECT s FROM CheckboxQuestion s WHERE s.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title", "position", "required", "survey"})
public class CheckboxQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "CheckboxQuestion";

    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Checkbox> checkboxes = new ArrayList<>();


    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();

    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne
    private Section section;


}

