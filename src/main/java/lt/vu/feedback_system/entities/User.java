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
@Table(schema = "FEEDBACK", name = "USERS")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT s FROM User s"),
        @NamedQuery(name = "User.findById", query = "SELECT s FROM User s WHERE s.id = :id"),
        @NamedQuery(name = "User.findByFirstName", query = "SELECT s FROM User s WHERE s.firstName LIKE :firstName"),
        @NamedQuery(name = "User.findByLastName", query = "SELECT s FROM User s WHERE s.lastName LIKE :lastName"),
})
@Getter
@Setter
@EqualsAndHashCode(of = {"firstName", "lastName", "email"})
@ToString(of = {"id", "firstName", "lastName", "email"})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 3, max = 20)
    @Column(name = "EMAIL")
    private String email;

    @Size(min = 3, max = 20)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Size(min = 3, max = 20)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ADMIN")
    private Boolean admin;

    @Column(name = "IS_BLOCKED")
    private Boolean blocked;

    @OneToMany(mappedBy = "creator")
    private List<Survey> createdSurveys = new ArrayList<>();
}
