package uol.compass.ms.order.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.exceptions.OrderNotFoundException;
import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
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

    @Override
    public Page<OrderResponseDTO> findAll(String cpf, Pageable pageable) {
        Page<OrderEntity> page = cpf == null
            ? orderRepository.findAll(pageable)
            : orderRepository.findByCpf(cpf, pageable);

        return page.map(order -> mapper.map(order, OrderResponseDTO.class));
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        OrderEntity order = getOrderEntity(id);

        return mapper.map(order, OrderResponseDTO.class);
    }

    private OrderEntity getOrderEntity(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderResponseDTO updateItems(Long id, List<ItemRequestDTO> items) {
        // TODO Auto-generated method stub
        return null;
    }
}
