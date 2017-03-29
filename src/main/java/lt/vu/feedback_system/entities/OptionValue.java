package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by kazim on 2017-03-26.
 */
@Entity
@Table(name = "FEEDBACK.OPTION_VALUES")
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
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 200)
    @Column(name = "TITLE")
    private String title;

    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private OptionQuestion question;
}

