package uol.compass.ms.order.service;

import uol.compass.ms.order.model.dto.OrderRequestDTO;
import uol.compass.ms.order.model.dto.OrderResponseDTO;

public interface OrderService {
    
    OrderResponseDTO create(OrderRequestDTO request);

}
