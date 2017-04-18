package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.TextAnswer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "text_questions")
@NamedQueries({
        @NamedQuery(name = "TextQuestion.findAll", query = "SELECT c FROM TextQuestion c"),
        @NamedQuery(name = "TextQuestion.findBySurveyId", query = "SELECT s FROM TextQuestion s WHERE s.survey = :survey")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "position",  "title"})
public class TextQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "TextQuestion";

    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @Column(name = "position")
    private Integer position;

    @Column(name = "is_required")
    private Boolean required;

    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<TextAnswer> textAnswers = new ArrayList<>();
}

