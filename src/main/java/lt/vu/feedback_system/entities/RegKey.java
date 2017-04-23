package lt.vu.feedback_system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(schema = "feedback", name = "reg_keys")
@Data
@EqualsAndHashCode(exclude = "id")
public class RegKey {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "expires", nullable = false)
    private Timestamp expires;
}
