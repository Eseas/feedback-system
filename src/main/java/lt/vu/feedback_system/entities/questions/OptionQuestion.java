package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.OptionAnswer;
import lt.vu.feedback_system.entities.Survey;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "option_questions")
@NamedQueries({
        @NamedQuery(name = "OptionQuestion.findAll", query = "SELECT c FROM OptionQuestion c"),
        @NamedQuery(name = "OptionQuestion.findBySurveyId", query = "SELECT s FROM OptionQuestion s WHERE s.survey = :survey")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title", "position", "required", "multiple", "survey"})
public class OptionQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "OptionQuestion";

    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @Column(name = "is_multiple")
    private Boolean multiple;

    @OneToMany(mappedBy = "question")
    private List<OptionValue> optionValues = new ArrayList<>();

    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<OptionAnswer> optionAnswers = new ArrayList<>();
}

