package uol.compass.mshistory.framework.adpater.in.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uol.compass.mshistory.application.service.OrderHistoryServiceImpl;
import uol.compass.mshistory.builder.ScenarioBuilder;
import uol.compass.mshistory.domain.dto.request.OrderHistoryRequestDTO;

@ExtendWith(MockitoExtension.class)
public class TopicListenerTest {

    @InjectMocks
    private TopicListener topicListener;

    @Mock
    private OrderHistoryServiceImpl historyService;

    @Test
    void testConsume() {
        OrderHistoryRequestDTO request = ScenarioBuilder.buildOrderHistoryRequestDTO();
        ConsumerRecord<String, OrderHistoryRequestDTO> payload = new ConsumerRecord<>("test", 0, 0, null, request);

        doNothing().when(historyService).create(any());

        topicListener.consume(payload);
        verify(historyService).create(any());
    }
}
