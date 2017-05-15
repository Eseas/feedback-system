/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.feedback_system.entities.surveys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.entities.questions.CheckboxQuestion;
import lt.vu.feedback_system.entities.questions.RadioQuestion;
import lt.vu.feedback_system.entities.questions.SliderQuestion;
import lt.vu.feedback_system.entities.questions.TextQuestion;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "surveys")
@NamedQueries({
    @NamedQuery(name = "Survey.findAll", query = "SELECT c FROM Survey c"),
    @NamedQuery(name = "Survey.findById", query = "SELECT c FROM Survey c WHERE c.id = :id"),
    @NamedQuery(name = "Survey.findByLink", query = "SELECT c FROM Survey c WHERE c.link = :link"),
    @NamedQuery(name = "Survey.findByName", query = "SELECT c FROM Survey c WHERE c.title = :title"),
    @NamedQuery(name = "Survey.findAllByCreatorId", query = "SELECT c FROM Survey c WHERE c.creator.id = :id")
})
@Getter
@Setter
@EqualsAndHashCode(of = "title")
@ToString(of = {"id", "title"})
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "link")
    private String link;

    @Size(max = 200)
    @Column(name = "title")
    private String title;

    @Size(max = 200)
    @Column(name = "description")
    private String description;

    @Column(name = "is_confidential")
    private Boolean confidential;

    @OneToMany(mappedBy = "survey")
    private List<AnsweredSurvey> answeredSurveys = new ArrayList<>();

    @ManyToOne(optional=false)
    @JoinColumn(
            name="creator_id", nullable=false, updatable=false)
    private User creator;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Section> sections = new ArrayList<>();
}
