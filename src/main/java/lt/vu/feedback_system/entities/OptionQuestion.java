package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazim on 2017-03-26.
 */
@Entity
@Table(name = "FEEDBACK.OPTION_QUESTIONS")
@NamedQueries({
        @NamedQuery(name = "OptionQuestion.findAll", query = "SELECT c FROM OptionQuestion c")})
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title"})
public class OptionQuestion {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 200)
    @Column(name = "TITLE")
    private String title;

    @Column(name = "REQUIRED")
    private Boolean required;

    @Column(name = "MULTIPLE")
    private Boolean multiple;

    @JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<OptionValue> optionValueList = new ArrayList<>();
}

