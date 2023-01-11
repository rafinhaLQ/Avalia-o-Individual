package uol.compass.ms.order.service;

import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;

import uol.compass.ms.order.model.dto.OrderRequestDTO;
import uol.compass.ms.order.model.dto.OrderResponseDTO;
import uol.compass.ms.order.model.entities.OrderEntity;

public class OrderServiceImpl implements OrderService {

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {
        OrderEntity orderToCreate = new OrderEntity();
        orderToCreate.setCpf(request.getCpf());
        // orderToCreate.setItems();
        orderToCreate.setTotal(request.getTotal());
        Cep cep = ViaCepClient.findCep(request.getCep());
    }
    
}
