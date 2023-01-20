package uol.compass.mshistory.builder;

import java.time.LocalDate;
import uol.compass.mshistory.domain.dto.request.OrderHistoryRequestDTO;
import uol.compass.mshistory.domain.dto.response.OrderHistoryResponseDTO;
import uol.compass.mshistory.domain.model.OrderHistory;

public class ScenarioBuilder {

    private static final String ID = "63c701fe06236c4192ef38ed";

    private static final Long ORDER_ID = 1L;

    private static final Double TOTAL = 10.5;

    private static final LocalDate ORDER_DATE = LocalDate.of(2023, 2, 21);

    public static OrderHistoryResponseDTO buildOrderHistoryResponseDTO() {
        return OrderHistoryResponseDTO.builder().id(ID).orderId(ORDER_ID).total(TOTAL).orderDate(ORDER_DATE).build();
    }

    public static OrderHistory buildOrderHistory() {
        return OrderHistory.builder().id(ID).orderId(ORDER_ID).total(TOTAL).orderDate(ORDER_DATE).build();
    }

    public static OrderHistoryRequestDTO buildOrderHistoryRequestDTO() {
        return OrderHistoryRequestDTO.builder().id(ORDER_ID).total(TOTAL).build();
    }
}
