/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @NamedQuery(name = "Survey.findByName", query = "SELECT c FROM Survey c WHERE c.title = :title")
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

    @Size(min = 4, max = 200)
    @Column(name = "title")
    private String title;

    @Size(min = 4, max = 200)
    @Column(name = "description")
    private String description;

    @Column(name = "is_confidential")
    private Boolean confidential;

    @OneToMany(mappedBy = "survey")
    private List<TextQuestion> textQuestionList = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<SliderQuestion> sliderQuestionList = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<OptionQuestion> optionQuestionList = new ArrayList<>();



    @ManyToOne(optional=false)
    @JoinColumn(
            name="creator_id", nullable=false, updatable=false)
    public User creator;


}
