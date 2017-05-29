package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "slider_questions")
@NamedQueries({
        @NamedQuery(name = "SliderQuestion.findBySectionId", query = "SELECT s FROM SliderQuestion s WHERE s.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "title"})
@ToString(of = {"id", "title", "position", "required"})
public class SliderQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "SliderQuestion";

    @Size(min = 1, max = 40)
    @Column(name = "title")
    private String title;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @Column(name = "lower_bound")
    private Integer lowerBound;

    @Column(name = "upper_bound")
    private Integer upperBound;

    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<SliderAnswer> sliderAnswers = new ArrayList<>();

    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne
    private Section section;

    @Override
    public List<? extends Answer> getAnswers() {
        return sliderAnswers;
    }
}

