package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;
import lt.vu.feedback_system.entities.surveys.AnsweredSurvey;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "checkbox_answers")
@NamedQueries({
        @NamedQuery(name = "CheckboxAnswer.findAllByQuestionId", query = "SELECT c FROM CheckboxAnswer c WHERE c.question.id = :id"),
        @NamedQuery(name = "CheckboxAnswer.findAll", query = "SELECT c FROM CheckboxAnswer c"),
        @NamedQuery(name = "CheckboxAnswer.findBySectionId", query = "SELECT s FROM CheckboxAnswer s WHERE s.question.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "question"})
public class CheckboxAnswer implements Answer {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne
    private CheckboxQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "id")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;

    @OneToMany(mappedBy = "checkboxAnswer", cascade = CascadeType.ALL)
    private List<SelectedCheckbox> selectedCheckboxes;

    @Transient
    private List<SelectedCheckbox> availableSelectedCheckboxes;
}