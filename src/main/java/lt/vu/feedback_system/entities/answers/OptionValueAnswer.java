package lt.vu.feedback_system.entities.answers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.OptionAnswer;
import lt.vu.feedback_system.entities.questions.OptionQuestion;
import lt.vu.feedback_system.entities.questions.OptionValue;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "option_value_answers")
@Getter
@Setter
public class OptionValueAnswer {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    @ManyToOne
    private OptionAnswer optionAnswer;

    @JoinColumn(name = "option_value_id", referencedColumnName = "id")
    @OneToOne
    private OptionValue optionValue;
}

