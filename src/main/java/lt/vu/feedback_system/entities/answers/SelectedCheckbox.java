package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.questions.Checkbox;

import javax.persistence.*;

@Entity
@Table(schema = "feedback", name = "selected_checkboxes")
@NamedQueries({
        @NamedQuery(name = "SelectedCheckbox.findById", query = "SELECT c FROM SelectedCheckbox c WHERE c.id = :id"),
        @NamedQuery(name = "SelectedCheckbox.findByQuestionId", query = "SELECT c FROM SelectedCheckbox c WHERE c.checkbox.question.id = :id"),
        @NamedQuery(name = "SelectedCheckbox.findByCheckboxId", query = "SELECT c FROM SelectedCheckbox c WHERE c.checkbox.id = :id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "checkbox"})
public class SelectedCheckbox {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    @ManyToOne
    private CheckboxAnswer checkboxAnswer;

    @JoinColumn(name = "checkbox_id", referencedColumnName = "id")
    @OneToOne
    private Checkbox checkbox;

}