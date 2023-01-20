package uol.compass.ms.order.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.application.port.in.OrderService;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderUpdateRequestDTO;
import uol.compass.ms.order.domain.dto.response.OrderHistoryResponseDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.adpater.out.OrderRepository;
import uol.compass.ms.order.framework.adpater.out.TopicProducer;
import uol.compass.ms.order.framework.exceptions.OrderNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final AddressServiceImpl addressService;
    private final ItemServiceImpl itemService;
    private final TopicProducer topicProducer;

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {
        log.info("Starting the method to create order");

        OrderEntity orderToCreate = new OrderEntity();

        orderToCreate.setCpf(request.getCpf());
        orderToCreate.setItems(itemService.createItems(request.getItems()));
        orderToCreate.setTotal(itemService.getTotalValue(request.getItems()));
        orderToCreate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderCreated = orderRepository.save(orderToCreate);

        log.info("Order created on database");

        topicProducer.send(mapper.map(orderCreated, OrderHistoryResponseDTO.class));

        log.info("Message sended to Kafka");

        return mapper.map(orderCreated, OrderResponseDTO.class);
    }

    @Override
    public Page<OrderResponseDTO> findAll(String cpf, Pageable pageable) {
        log.info("Starting the method to find orders in database");

        Page<OrderEntity> page = cpf == null
            ? orderRepository.findAll(pageable)
            : orderRepository.findByCpf(cpf, pageable);

        log.info("Orders searched on database");

        return page.map(order -> mapper.map(order, OrderResponseDTO.class));
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        log.info("Starting the method to find order " + id + " in database");
        OrderEntity order = getOrderEntity(id);

        log.info("Order " + id + " found on database");

        return mapper.map(order, OrderResponseDTO.class);
    }

    private OrderEntity getOrderEntity(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderResponseDTO updateItems(Long id, List<ItemRequestDTO> items) {
        log.info("Starting the method to update items on order " + id + " in database");
        OrderEntity orderToUpdate = getOrderEntity(id);

        orderToUpdate.setItems(itemService.createItems(items));
        orderToUpdate.setTotal(itemService.getTotalValue(items));

        OrderEntity orderUpdated = orderRepository.save(orderToUpdate);

        log.info("Items on order " + id + " updated on database");

        topicProducer.send(mapper.map(orderUpdated, OrderHistoryResponseDTO.class));

        log.info("Message sended to Kafka");

        return mapper.map(orderUpdated, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderUpdateRequestDTO request) {
        log.info("Starting the method to update order " + id + " in database");

        OrderEntity orderToUpdate = getOrderEntity(id);

        orderToUpdate.setCpf(request.getCpf());
        orderToUpdate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderUpdated = orderRepository.save(orderToUpdate);

        log.info("Order " + id + " updated on database");

        return mapper.map(orderUpdated, OrderResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        log.info("Starting the method to delete order " + id + " in database");

        getOrderEntity(id);

        orderRepository.deleteById(id);

        log.info("Order " + id + " deleted on database");
    }
}
