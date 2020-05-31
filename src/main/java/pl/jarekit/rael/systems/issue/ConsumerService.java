package pl.jarekit.rael.systems.issue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
class ConsumerService {

    private MessageRepo messageRepo;
    private RabbitTemplate rabbitTemplate;
    private String queue;

    @Autowired
    public ConsumerService(MessageRepo messageRepo, RabbitTemplate rabbitTemplate,
                           @Value("${rael.rabbitmq.queue}") String queue) {
        this.messageRepo = messageRepo;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    List<Message> receiveOldIssues() {
        return (List<Message>) messageRepo.findAll();
    }

    void deleteMessage(long id) {
        messageRepo.deleteById(id);
    }
    
    List<Message> receiveNewIssues() {

        List<Message> messageList = new LinkedList<>();
        org.springframework.amqp.core.Message messageFull;
        byte[] messageByte;

        do {
            messageFull = rabbitTemplate.receive(queue);
            try {
                messageByte = messageFull.getBody();
            } catch (NullPointerException e) {
                return messageList;
            }

            Message msg = deserializeMessage(messageByte);
            messageList.add(msg);
            messageRepo.save(msg);
        }
        while (checkIfThereIsNextMessage(messageFull));

        return messageList;
    }

    private Message deserializeMessage(byte[] messageByte) {
        return (Message) SerializationUtils.deserialize(messageByte);
    }

    private boolean checkIfThereIsNextMessage(org.springframework.amqp.core.Message messageFull) {
        return messageFull.getMessageProperties().getMessageCount() > 0;
    }
}
