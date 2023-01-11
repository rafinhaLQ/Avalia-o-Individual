package uol.compass.ms.order.service;

import uol.compass.ms.order.model.dto.request.OrderRequestDTO;
import uol.compass.ms.order.model.dto.response.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO request);

}
