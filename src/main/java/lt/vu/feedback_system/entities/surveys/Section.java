package lt.vu.feedback_system.entities.surveys;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.questions.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "sections")
@NamedQueries({
        @NamedQuery(name = "Section.findAll", query = "SELECT c FROM Section c"),
        @NamedQuery(name = "Section.findById", query = "SELECT c FROM Section c WHERE c.id = :id"),
        @NamedQuery(name = "Section.findAllBySurveyId", query = "SELECT c FROM Section c WHERE c.survey.id = :id")
})
@Getter
@Setter
public class Section {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "position")
    private Integer position;

    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;

    @Transient
    private List<Question> questions = new ArrayList<>();

    @Transient
    private List<Answer> answers = new ArrayList<>();

    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @ManyToOne
    private Survey survey;
}
