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
import lt.vu.feedback_system.entities.answers.OptionAnswer;
import lt.vu.feedback_system.entities.answers.SliderAnswer;
import lt.vu.feedback_system.entities.answers.TextAnswer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "answered_surveys")
@NamedQueries({
        @NamedQuery(name = "AnsweredSurvey.findAll", query = "SELECT c FROM AnsweredSurvey c"),
})
@Getter
@Setter
@EqualsAndHashCode(of = "survey")
@ToString(of = {"id", "survey"})
public class AnsweredSurvey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "answeredSurvey")
    private List<OptionAnswer> optionAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "answeredSurvey")
    private List<SliderAnswer> sliderAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "answeredSurvey")
    private List<TextAnswer> textAnswers = new ArrayList<>();
}
