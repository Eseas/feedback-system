package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.AnsweredSurvey;
import lt.vu.feedback_system.entities.questions.SliderQuestion;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "slider_answers")
@NamedQueries({
        @NamedQuery(name = "SliderAnswer.findAll", query = "SELECT c FROM SliderAnswer c")})
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

    @Transient
    private final String type = "text";

    @Column(name = "value")
    private Integer value;

    @JoinColumn(name = "question_id", referencedColumnName = "ID")
    @ManyToOne
    private SliderQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "ID")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;
}