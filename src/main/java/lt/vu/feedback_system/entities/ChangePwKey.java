package lt.vu.feedback_system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;


@Entity
@Table(schema = "feedback", name = "change_pw_keys")
@NamedQueries({
        @NamedQuery(name = "ChangePwKey.selectByCode", query = "SELECT k FROM ChangePwKey k WHERE k.code = :code"),
        @NamedQuery(name = "ChangePwKey.deleteByUserId", query = "DELETE FROM ChangePwKey k WHERE k.user.id = :user_id")
})
@Data
@EqualsAndHashCode(exclude = "id")
public class ChangePwKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    public ChangePwKey() {}

    public ChangePwKey(String code, User user) {
        this.code = code;
        this.user = user;
    }

}
