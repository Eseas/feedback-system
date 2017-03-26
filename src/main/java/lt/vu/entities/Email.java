package lt.vu.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by kazim on 2017-03-26.
 */
@Entity
@Table(name = "STUDENT")
@NamedQueries({
        @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
        @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
        @NamedQuery(name = "Student.findByFirstName", query = "SELECT s FROM Student s WHERE s.firstName LIKE :firstName"),
        @NamedQuery(name = "Student.findByLastName", query = "SELECT s FROM Student s WHERE s.lastName LIKE :lastName"),
        @NamedQuery(name = "Student.findByRegistrationNo", query = "SELECT s FROM Student s WHERE s.registrationNo = :registrationNo")
})
@Getter
@Setter
@EqualsAndHashCode(of = "email")
@ToString(of = {"id", "email"})
public class Email {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 3, max = 40)
    @Column(name = "email")
    private String email;
}
