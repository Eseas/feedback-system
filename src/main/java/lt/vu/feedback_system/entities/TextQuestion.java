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
@Table(schema = "FEEDBACK", name = "TEXT_QUESTIONS")
@NamedQueries({
        @NamedQuery(name = "TextQuestion.findAll", query = "SELECT c FROM TextQuestion c")})
@Getter
@Setter
@EqualsAndHashCode(of = {"title"})
@ToString(of = {"id", "title"})
public class TextQuestion {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_REQUIRED")
    private Boolean required;

    @Size(min = 4, max = 200)
    @Column(name = "TITLE")
    private String title;

    @JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;
}

