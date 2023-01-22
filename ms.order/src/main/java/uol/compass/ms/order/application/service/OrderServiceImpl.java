package uol.compass.ms.order.application.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.application.port.in.AddressService;
import uol.compass.ms.order.application.port.in.ItemService;
import uol.compass.ms.order.application.port.in.OrderService;
import uol.compass.ms.order.application.port.out.OrderRepositoryPortOut;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderUpdateRequestDTO;
import uol.compass.ms.order.domain.dto.response.OrderHistoryResponseDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.adpater.out.event.TopicProducer;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryPortOut orderRepository;
    private final ModelMapper mapper;
    private final AddressService addressService;
    private final ItemService itemService;
    private final TopicProducer topicProducer;

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {
        log.info("Starting the method to create order");

        OrderEntity orderToCreate = new OrderEntity();

        orderToCreate.setCpf(request.getCpf());
        orderToCreate.setItems(request.getItemsIds().stream().map(itemService::findById).collect(Collectors.toList()));
        orderToCreate.setTotal(
            request.getItemsIds().stream().map(itemService::findById).mapToDouble(ItemEntity::getValue).sum()
        );
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
            ? orderRepository.findAllOrders(pageable)
            : orderRepository.findAllOrdersByCpf(cpf, pageable);

        log.info("Orders searched on database");

        return page.map(order -> mapper.map(order, OrderResponseDTO.class));
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        log.info("Starting the method to find order " + id + " in database");

        OrderEntity order = orderRepository.findById(id);

        log.info("Order " + id + " found on database");

        return mapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO updateItems(Long id, List<Long> itemsIds) {
        log.info("Starting the method to update items on order " + id + " in database");

        OrderEntity orderToUpdate = orderRepository.findById(id);

        orderToUpdate.setItems(itemsIds.stream().map(itemService::findById).collect(Collectors.toList()));
        orderToUpdate.setTotal(itemsIds.stream().map(itemService::findById).mapToDouble(ItemEntity::getValue).sum());

        OrderEntity orderUpdated = orderRepository.save(orderToUpdate);

        log.info("Items on order " + id + " updated on database");

        topicProducer.send(mapper.map(orderUpdated, OrderHistoryResponseDTO.class));

        log.info("Message sended to Kafka");

        return mapper.map(orderUpdated, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderUpdateRequestDTO request) {
        log.info("Starting the method to update order " + id + " in database");

        OrderEntity orderToUpdate = orderRepository.findById(id);

        orderToUpdate.setCpf(request.getCpf());
        orderToUpdate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderUpdated = orderRepository.save(orderToUpdate);

        log.info("Order " + id + " updated on database");

        return mapper.map(orderUpdated, OrderResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        log.info("Starting the method to delete order " + id + " in database");

        orderRepository.findById(id);

        orderRepository.delete(id);

        log.info("Order " + id + " deleted on database");
    }
}
