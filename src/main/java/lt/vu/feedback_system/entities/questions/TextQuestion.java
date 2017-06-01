package lt.vu.feedback_system.entities.questions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.Answer;
import lt.vu.feedback_system.entities.answers.TextAnswer;
import lt.vu.feedback_system.entities.surveys.Section;
import lt.vu.feedback_system.entities.surveys.Survey;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Entity
@Table(schema = "feedback", name = "text_questions")
@NamedQueries({
        @NamedQuery(name = "TextQuestion.findBySectionId", query = "SELECT s FROM TextQuestion s WHERE s.section.id = :section_id")
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "title"})
@ToString(of = {"id", "title", "position", "required"})
public class TextQuestion implements Question {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Transient
    private final String type = "TextQuestion";

    @Size(max = 40)
    @Column(name = "title")
    private String title;

    @Column(name = "position")
    private Integer position;

    @Column(name = "is_required")
    private Boolean required;

    @JoinColumn(name = "survey_id", referencedColumnName = "ID")
    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<TextAnswer> textAnswers = new ArrayList<>();

    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne
    private Section section;

    @Transient
    public Future<TagCloudModel> model;

    @Override
    public List<? extends Answer> getAnswers() {
        return textAnswers;
    }
}

