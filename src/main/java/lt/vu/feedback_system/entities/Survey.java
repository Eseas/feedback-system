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
@Table(name = "FEEDBACK.SURVEYS")
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
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 200)
    @Column(name = "TITLE")
    private String title;

    @Size(min = 4, max = 200)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONFIDENTIAL")
    private Boolean confidential;

    @OneToMany(mappedBy = "survey")
    private List<TextQuestion> textQuestionList = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<SliderQuestion> sliderQuestionList = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<OptionQuestion> optionQuestionList = new ArrayList<>();



    @ManyToOne(optional=false)
    @JoinColumn(
            name="CREATOR_ID", nullable=false, updatable=false)
    public User creator;


}
