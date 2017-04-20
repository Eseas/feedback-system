package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.AnsweredSurvey;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "option_answers")
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

    @Transient
    private final String type = "text";

    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne
    private CheckboxQuestion question;

    @JoinColumn(name = "answered_survey_id", referencedColumnName = "id")
    @ManyToOne
    private AnsweredSurvey answeredSurvey;

    @OneToMany(mappedBy = "checkboxAnswer")
    private List<SelectedCheckbox> selectedCheckboxes;

//    Justas approach
//
//    @OneToMany
//    private List<Checkbox> optionValues;

}