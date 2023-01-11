package uol.compass.ms.order.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.OrderRequestDTO;
import uol.compass.ms.order.model.dto.OrderResponseDTO;
import uol.compass.ms.order.model.entities.OrderEntity;
import uol.compass.ms.order.repositories.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final AddressService addressService;

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {
        OrderEntity orderToCreate = new OrderEntity();
        
        orderToCreate.setCpf(request.getCpf());
        // orderToCreate.setItems();
        orderToCreate.setTotal(request.getTotal());
        orderToCreate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderCreated = orderRepository.save(orderToCreate);

        return mapper.map(orderCreated, OrderResponseDTO.class);
    }
    
}
