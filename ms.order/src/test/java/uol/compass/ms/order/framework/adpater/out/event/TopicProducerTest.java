package uol.compass.ms.order.framework.adpater.out.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.response.OrderHistoryResponseDTO;

@ExtendWith(MockitoExtension.class)
public class TopicProducerTest {

    @Value("${topic.name.producer}")
    private String topicName;

    @InjectMocks
    private TopicProducer topicProducer;

    @Mock
    private KafkaTemplate<String, OrderHistoryResponseDTO> kafkaTemplate;

    @Test
    void testSendEvent() {
        OrderHistoryResponseDTO orderHistory = ScenarioBuilder.buildOrderHistoryResponseDTO();

        kafkaTemplate.send(topicName, orderHistory);

        topicProducer.send(orderHistory);
    }
}
