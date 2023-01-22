package uol.compass.ms.order.framework.adpater.out.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uol.compass.ms.order.application.port.out.OrderRepositoryPortOut;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.exceptions.OrderNotFoundException;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapterOut implements OrderRepositoryPortOut {

    private final OrderMySqlRepository mySqlRepository;

    @Override
    public OrderEntity save(OrderEntity order) {
        return mySqlRepository.save(order);
    }

    @Override
    public Page<OrderEntity> findAllOrders(Pageable pageable) {
        return mySqlRepository.findAll(pageable);
    }

    @Override
    public Page<OrderEntity> findAllOrdersByCpf(String cpf, Pageable pageable) {
        return mySqlRepository.findByCpf(cpf, pageable);
    }

    @Override
    public OrderEntity findById(Long id) {
        return mySqlRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        mySqlRepository.deleteById(id);
    }
}
