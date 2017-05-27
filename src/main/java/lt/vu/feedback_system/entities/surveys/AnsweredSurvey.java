package lt.vu.feedback_system.entities.surveys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.answers.CheckboxAnswer;
import lt.vu.feedback_system.entities.answers.RadioAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "answered_surveys")
@NamedQueries({
        @NamedQuery(name = "AnsweredSurvey.findById", query = "SELECT c FROM AnsweredSurvey c WHERE c.id = :id"),
        @NamedQuery(name = "AnsweredSurvey.findAll", query = "SELECT c FROM AnsweredSurvey c"),
        @NamedQuery(name = "AnsweredSurvey.findAllBySurveyId", query = "SELECT c FROM AnsweredSurvey c WHERE c.survey.id = :id")
})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id"})
public class AnsweredSurvey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "answeredSurvey", orphanRemoval = true)
    private List<TextAnswer> textAnswers;

    @OneToMany(mappedBy = "answeredSurvey", orphanRemoval = true)
    private List<SliderAnswer> sliderAnswers;

    @OneToMany(mappedBy = "answeredSurvey", orphanRemoval = true)
    private List<CheckboxAnswer> checkboxAnswers;

    @OneToMany(mappedBy = "answeredSurvey", orphanRemoval = true)
    private List<RadioAnswer> radioAnswers;
}
