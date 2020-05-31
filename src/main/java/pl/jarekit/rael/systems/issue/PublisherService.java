package pl.jarekit.rael.systems.issue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.service.LoginService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class PublisherService {

    private LoginService loginService;
    private RabbitTemplate rabbitTemplate;
    private String queue;

    @Autowired
    public PublisherService(LoginService loginService, RabbitTemplate rabbitTemplate,
                            @Value("${rael.rabbitmq.queue}") String queue) {
        this.loginService = loginService;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    void publishMessage(String topic, String message) {
        Message messageObj = createMessage(topic, message);
        byte[] messageByte = serializeMessage(messageObj);
        rabbitTemplate.convertAndSend(queue, messageByte);
    }

    private Message createMessage(String topic, String message) {
        return new Message(
                LocalDateTime.now(ZoneOffset.UTC).plusHours(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                loginService.getUser().getUsername(),
                topic,
                message
        );
    }

    private byte[] serializeMessage(Message messageObj){
        return SerializationUtils.serialize(messageObj);
    }
}
