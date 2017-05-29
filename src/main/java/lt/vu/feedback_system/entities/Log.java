package lt.vu.feedback_system.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(schema = "feedback", name = "logs")
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"time", "text"})
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private java.util.Date time;

    @Size(max = 100)
    @Column(name = "text")
    private String text;

    public Log() {}

    public Log(String text) {
        this.time = new Date();
        this.text = text;
    }

}
