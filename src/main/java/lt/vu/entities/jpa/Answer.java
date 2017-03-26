package lt.vu.entities.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by kazim on 2017-02-24.
 */

@Entity
@Table(name = "ANSWER")
@NamedQueries({
        @NamedQuery(name = "Answer.findAll", query = "SELECT c FROM Answer c"),
        @NamedQuery(name = "Answer.findById", query = "SELECT c FROM Answer c WHERE c.id = :id"),
        @NamedQuery(name = "Answer.findByOptLockVersion", query = "SELECT c FROM Answer c WHERE c.optLockVersion = :optLockVersion")})
@Getter
@Setter
@EqualsAndHashCode(of = {"answerText"})
@ToString(of = {"id", "answerText"})
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 100)
    @Column(name = "answer_text")
    private String answerText;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    private Question questionId;
}