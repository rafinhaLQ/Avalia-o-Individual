package uol.compass.ms.order.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
import uol.compass.ms.order.model.dto.request.OrderRequestDTO;
import uol.compass.ms.order.model.dto.response.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO request);

    Page<OrderResponseDTO> findAll(String cpf, Pageable pageable);

    OrderResponseDTO findById(Long id);

    OrderResponseDTO updateItems(Long id, List<ItemRequestDTO> items);

}
