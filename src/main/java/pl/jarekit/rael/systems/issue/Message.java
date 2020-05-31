package pl.jarekit.rael.systems.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter @Setter
@Entity
@ToString
@NoArgsConstructor
class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String localDateTime;
    private String user;
    private String topic;
    private String message;

    Message(String localDateTime, String user, String topic, String message) {
        this.localDateTime = localDateTime;
        this.user = user;
        this.topic = topic;
        this.message = message;
    }
}
