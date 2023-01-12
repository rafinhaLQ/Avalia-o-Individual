package uol.compass.ms.order.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.request.OrderRequestDTO;
import uol.compass.ms.order.model.dto.response.OrderResponseDTO;
import uol.compass.ms.order.model.entities.OrderEntity;
import uol.compass.ms.order.repositories.OrderRepository;
import uol.compass.ms.order.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final AddressServiceImpl addressService;
    private final ItemServiceImpl itemService;

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {
        OrderEntity orderToCreate = new OrderEntity();
        
        orderToCreate.setCpf(request.getCpf());
        orderToCreate.setItems(itemService.createItems(request.getItems()));
        orderToCreate.setTotal(itemService.getTotalValue(request.getItems()));
        orderToCreate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderCreated = orderRepository.save(orderToCreate);

        return mapper.map(orderCreated, OrderResponseDTO.class);
    }
    
}
