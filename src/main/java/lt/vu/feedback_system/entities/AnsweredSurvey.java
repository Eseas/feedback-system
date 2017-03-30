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
@Table(schema = "FEEDBACK", name = "ANSWERED_SURVEYS")
@NamedQueries({
        @NamedQuery(name = "AnsweredSurvey.findAll", query = "SELECT c FROM AnsweredSurvey c"),
})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id"})
public class AnsweredSurvey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="SURVEY_ID")
    private Survey survey;
}
