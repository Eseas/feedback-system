package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;
import lt.vu.feedback_system.entities.questions.RadioButton;
import lt.vu.feedback_system.entities.questions.RadioQuestion;

import javax.persistence.*;

@Entity
@Table(schema = "feedback", name = "radio_answers")
@NamedQueries({
        @NamedQuery(name = "RadioAnswer.findAllByQuestionId", query = "SELECT c FROM RadioAnswer c WHERE c.question.id = :id"),
        @NamedQuery(name = "RadioAnswer.findAll", query = "SELECT c FROM RadioAnswer c"),
        @NamedQuery(name = "RadioAnswer.findBySectionId", query = "SELECT s FROM RadioAnswer s WHERE s.question.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "question"})
public class RadioAnswer implements Answer {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne
    private RadioQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "id")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;

    @JoinColumn(name = "radio_button_id", referencedColumnName = "id")
    @OneToOne
    private RadioButton radioButton;

}