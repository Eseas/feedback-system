package lt.vu.feedback_system.entities.surveys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
}
