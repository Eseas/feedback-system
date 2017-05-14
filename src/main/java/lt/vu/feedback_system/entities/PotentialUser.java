package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "feedback", name = "potential_users")
@NamedQueries({
        @NamedQuery(name = "PotentialUser.findAll", query = "SELECT s FROM PotentialUser s"),
        @NamedQuery(name = "PotentialUser.selectByEmail", query = "SELECT u FROM PotentialUser u WHERE u.email = :email")
})
@Getter
@Setter
@EqualsAndHashCode(of = "email")
@ToString(of = {"id", "email"})
public class PotentialUser {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 40)
    @Column(name = "email", unique = true)
    private String email;
}
