package uol.compass.mshistory.framework.adpater.in.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uol.compass.mshistory.application.port.in.OrderHistoryService;
import uol.compass.mshistory.domain.dto.request.OrderHistoryRequestDTO;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    private final OrderHistoryService orderHistoryService;

    @Value("${topic.name.consumer}")
    private String topicName;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, OrderHistoryRequestDTO> payload) {
        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

        orderHistoryService.create(payload.value());
    }
}
