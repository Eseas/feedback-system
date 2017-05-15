package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.vu.feedback_system.entities.surveys.Survey;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "feedback", name = "users")
@NamedQueries({
        @NamedQuery(name = "User.countByEmail", query = "SELECT COUNT(u) FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findAll", query = "SELECT s FROM User s"),
        @NamedQuery(name = "User.findById", query = "SELECT s FROM User s WHERE s.id = :id"),
        @NamedQuery(name = "User.findByFirstName", query = "SELECT s FROM User s WHERE s.firstName LIKE :firstName"),
        @NamedQuery(name = "User.findByLastName", query = "SELECT s FROM User s WHERE s.lastName LIKE :lastName"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT s FROM User s WHERE s.email LIKE :email"),
        @NamedQuery(name = "User.findByOptLockVersion", query = "SELECT s FROM User s WHERE s.optLockVersion = :optLockVersion"),
})
@Getter
@Setter
@EqualsAndHashCode(of = {"firstName", "lastName", "email"})
@ToString(of = {"id", "firstName", "lastName", "email"})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 40)
    @Column(name = "email")
    private String email;

    @Size(max = 20)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 20)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private Boolean admin;

    @Column(name = "is_blocked")
    private Boolean blocked;

    @OneToMany(mappedBy = "creator")
    private List<Survey> createdSurveys = new ArrayList<>();

    @Version
    @Column(name = "opt_lock_version")
    private Integer optLockVersion;

    public User() {}

    public User(String firstName, String lastName, String email, String password, boolean isAdmin, boolean isBlocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = isAdmin;
        this.blocked = isBlocked;
    }

}
