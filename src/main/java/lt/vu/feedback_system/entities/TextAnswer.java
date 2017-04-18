package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.questions.TextQuestion;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "text_answers")
@NamedQueries({
        @NamedQuery(name = "TextAnswer.findAll", query = "SELECT c FROM TextAnswer c")})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id","question","survey","value"})
public class TextAnswer {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @JoinColumn(name = "question_id", referencedColumnName = "ID")
    @ManyToOne
    private TextQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "ID")
    @ManyToOne
    private AnsweredSurvey survey;

}