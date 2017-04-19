package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.OptionAnswer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "option_values")
@NamedQueries({
        @NamedQuery(name = "OptionValue.findAll", query = "SELECT c FROM OptionValue c")})
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title"})
public class OptionValue {
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
    private OptionQuestion question;
//
//    @OneToMany(mappedBy = "value") // bad mapping name here
//    private List<OptionAnswer> optionAnswers = new ArrayList<>();
}

