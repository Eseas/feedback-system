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
@Table(name = "PERSON")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT s FROM Person s"),
    @NamedQuery(name = "Person.findById", query = "SELECT s FROM Person s WHERE s.id = :id"),
    @NamedQuery(name = "Person.findByFirstName", query = "SELECT s FROM Person s WHERE s.firstName LIKE :firstName"),
    @NamedQuery(name = "Person.findByLastName", query = "SELECT s FROM Person s WHERE s.lastName LIKE :lastName"),
})
@Getter
@Setter
@EqualsAndHashCode(of = {"firstName", "lastName", "email"})
@ToString(of = {"id", "firstName", "lastName", "email"})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 3, max = 20)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Size(min = 3, max = 20)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Size(min = 3, max = 20)
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;

    @JoinTable(name = "PERSON_SURVEY", joinColumns = {
            @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Survey> surveyList = new ArrayList<>();


}
