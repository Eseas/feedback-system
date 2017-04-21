package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "feedback", name = "radio_buttons")
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title"})
public class RadioButton {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne
    private RadioQuestion question;
//
//    @OneToMany(mappedBy = "value") // bad mapping name here
//    private List<CheckboxAnswer> optionAnswers = new ArrayList<>();
}

