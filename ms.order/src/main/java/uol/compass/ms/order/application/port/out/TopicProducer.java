package uol.compass.ms.order.application.port.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.domain.dto.response.OrderHistoryResponseDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, OrderHistoryResponseDTO> kafkaTemplate;

    public void send(OrderHistoryResponseDTO message) {
        log.info("Payload sended: {}", message);
        kafkaTemplate.send(topicName, message);
    }
}
