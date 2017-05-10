package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.questions.SliderQuestion;

import javax.persistence.*;

@Entity
@Table(schema = "feedback", name = "slider_answers")
@NamedQueries({
        @NamedQuery(name = "SliderAnswer.findAllByQuestionId", query = "SELECT c FROM SliderAnswer c WHERE c.question.id = :id"),
        @NamedQuery(name = "SliderAnswer.findAll", query = "SELECT c FROM SliderAnswer c"),
        @NamedQuery(name = "SliderAnswer.findBySectionId", query = "SELECT s FROM SliderAnswer s WHERE s.question.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "question", "value"})
public class SliderAnswer implements Answer{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private Integer value;

    @JoinColumn(name = "question_id", referencedColumnName = "ID")
    @ManyToOne
    private SliderQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "ID")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;
}