package lt.vu.feedback_system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(schema = "feedback", name = "reg_keys")
@NamedQueries({
    @NamedQuery(name = "RegKey.selectByCode", query = "SELECT k FROM RegKey k WHERE k.code = :code"),
    @NamedQuery(name = "RegKey.deleteByUserId", query = "DELETE FROM RegKey k WHERE k.user.id = :user_id")
})
@Data
@EqualsAndHashCode(exclude = "id")
public class RegKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @Column(name = "expires", nullable = false)
    private LocalDateTime expires;

    @JoinColumn(name = "potential_user_id", referencedColumnName = "id")
    @ManyToOne
    private PotentialUser user;

    public RegKey() {}

    public RegKey(String code, LocalDateTime expires, PotentialUser user) {
        this.code = code;
        this.expires = expires;
        this.user = user;
    }

}
