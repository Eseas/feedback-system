/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.entities.jpa;

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
@Table(name = "SURVEY")
@NamedQueries({
    @NamedQuery(name = "Survey.findAll", query = "SELECT c FROM Survey c"),
    @NamedQuery(name = "Survey.findById", query = "SELECT c FROM Survey c WHERE c.id = :id"),
    @NamedQuery(name = "Survey.findByName", query = "SELECT c FROM Survey c WHERE c.name = :name"),
    @NamedQuery(name = "Survey.findByOptLockVersion", query = "SELECT c FROM Survey c WHERE c.optLockVersion = :optLockVersion")})
@Getter
@Setter
@EqualsAndHashCode(of = "name")
@ToString(of = {"id", "name"})
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 50)
    @Column(name = "NAME")
    private String name;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;

    @ManyToMany(mappedBy = "surveyList")
    private List<Person> personList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyId")
    private List<Question> questionList = new ArrayList<>();
}
