package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.questions.TextQuestion;

import javax.persistence.*;

@Entity
@Table(schema = "feedback", name = "text_answers")
@NamedQueries({
        @NamedQuery(name = "TextAnswer.findAllByQuestionId", query = "SELECT c FROM TextAnswer c WHERE c.question.id = :id"),
        @NamedQuery(name = "TextAnswer.findAll", query = "SELECT c FROM TextAnswer c"),
        @NamedQuery(name = "TextAnswer.findBySectionId", query = "SELECT s FROM TextAnswer s WHERE s.question.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "question", "value"})
public class TextAnswer implements Answer {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne
    private TextQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "id")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;

}