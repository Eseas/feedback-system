package lt.vu.entities.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazim on 2017-02-24.
 */

@Entity
@Table(name = "QUESTION")
@NamedQueries({
        @NamedQuery(name = "Question.findAll", query = "SELECT c FROM Question c"),
        @NamedQuery(name = "Question.findById", query = "SELECT c FROM Question c WHERE c.id = :id"),
        @NamedQuery(name = "Question.findByOptLockVersion", query = "SELECT c FROM Question c WHERE c.optLockVersion = :optLockVersion")})
@Getter
@Setter
@EqualsAndHashCode(of = {"text"})
@ToString(of = {"id", "text"})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 100)
    @Column(name = "TEXT")
    private String text;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;

    @ManyToOne
    @JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")
    private Survey surveyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<Answer> answers = new ArrayList<>();
}