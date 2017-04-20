package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.Survey;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "radio_questions")
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title", "position", "required", "survey"})
public class RadioQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "RadioQuestion";

    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "question")
    private List<RadioButton> radioButtons = new ArrayList<>();

    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

}

