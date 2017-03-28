package lt.vu.feedback_system.entities;

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
@Table(name = "feedback.POTENTIAL_USERS")
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
    @Column(name = "ID")
    private Integer id;

    @Size(min = 3, max = 40)
    @Column(name = "EMAIL")
    private String email;
}
