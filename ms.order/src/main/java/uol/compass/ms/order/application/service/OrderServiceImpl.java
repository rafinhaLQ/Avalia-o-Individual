package uol.compass.ms.order.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.application.port.in.OrderService;
import uol.compass.ms.order.application.port.out.TopicProducer;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderUpdateRequestDTO;
import uol.compass.ms.order.domain.dto.response.OrderHistoryResponseDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.adpater.out.OrderRepository;
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
        OrderEntity orderToCreate = new OrderEntity();

        orderToCreate.setCpf(request.getCpf());
        orderToCreate.setItems(itemService.createItems(request.getItems()));
        orderToCreate.setTotal(itemService.getTotalValue(request.getItems()));
        orderToCreate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderCreated = orderRepository.save(orderToCreate);

        log.info("Pedido criado no banco");

        topicProducer.send(mapper.map(orderCreated, OrderHistoryResponseDTO.class));

        log.info("Mensagem enviada para o kafka");

        return mapper.map(orderCreated, OrderResponseDTO.class);
    }

    @Override
    public Page<OrderResponseDTO> findAll(String cpf, Pageable pageable) {
        Page<OrderEntity> page = cpf == null
            ? orderRepository.findAll(pageable)
            : orderRepository.findByCpf(cpf, pageable);

        log.info("Pedidos buscados no banco");

        return page.map(order -> mapper.map(order, OrderResponseDTO.class));
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        OrderEntity order = getOrderEntity(id);

        log.info("Pedido " + id + " encontrado no banco");

        return mapper.map(order, OrderResponseDTO.class);
    }

    private OrderEntity getOrderEntity(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderResponseDTO updateItems(Long id, List<ItemRequestDTO> items) {
        OrderEntity orderToUpdate = getOrderEntity(id);

        orderToUpdate.setItems(itemService.createItems(items));
        orderToUpdate.setTotal(itemService.getTotalValue(items));

        OrderEntity orderUpdated = orderRepository.save(orderToUpdate);

        log.info("Itens do pedido " + id + " alterado no banco");

        return mapper.map(orderUpdated, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderUpdateRequestDTO request) {
        OrderEntity orderToUpdate = getOrderEntity(id);

        orderToUpdate.setCpf(request.getCpf());
        orderToUpdate.setAddress(addressService.createAddressWithCep(request.getCep(), request.getNumber()));

        OrderEntity orderUpdated = orderRepository.save(orderToUpdate);

        log.info("Pedido " + id + " alterado no banco");

        return mapper.map(orderUpdated, OrderResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        getOrderEntity(id);

        orderRepository.deleteById(id);

        log.info("Pedido " + id + " removido do banco");
    }
}
