package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.AnsweredSurvey;
import lt.vu.feedback_system.entities.questions.OptionQuestion;
import lt.vu.feedback_system.entities.questions.OptionValue;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "option_answers")
@NamedQueries({
        @NamedQuery(name = "OptionAnswer.findAll", query = "SELECT c FROM OptionAnswer c")})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "question"})
public class OptionAnswer implements Answer {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "text";

    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne
    private OptionQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "id")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;

    @JoinColumn(name = "option_id", referencedColumnName = "id")
    @ManyToOne
    private OptionValue value;

}