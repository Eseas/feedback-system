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
@Table(schema = "feedback", name = "text_questions")
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
    @Column(name = "id")
    private Integer id;

    @Column(name = "is_required")
    private Boolean required;

    @Size(min = 4, max = 200)
    @Column(name = "title")
    private String title;

    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @ManyToOne
    private Survey survey;
    @OneToMany(mappedBy = "question")
    private List<TextAnswer> textAnswerList = new ArrayList<>();
}

