package lt.vu.feedback_system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(schema = "feedback", name = "reg_keys")
@NamedQueries({
    @NamedQuery(name = "RegKey.findAll", query = "SELECT k FROM RegKey k"),
    @NamedQuery(name = "RegKey.findByCode", query = "SELECT k FROM RegKey k WHERE k.code = :code")
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

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "expires", nullable = false)
    private LocalDateTime expires;

}
