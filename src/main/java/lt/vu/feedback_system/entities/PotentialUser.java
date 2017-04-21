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

    @Size(min = 3, max = 40)
    @Column(name = "email")
    private String email;
}
