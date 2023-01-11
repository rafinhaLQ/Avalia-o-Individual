package uol.compass.ms.order.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.OrderRequestDTO;
import uol.compass.ms.order.model.dto.OrderResponseDTO;
import uol.compass.ms.order.model.entities.AddressEntity;
import uol.compass.ms.order.model.entities.OrderEntity;
import uol.compass.ms.order.repositories.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {
        OrderEntity orderToCreate = new OrderEntity();
        AddressEntity address = new AddressEntity();
        
        Cep cep = ViaCepClient.findCep(request.getCep());

        address.setStreet(cep.getLogradouro());
        address.setNumber(request.getNumber());
        address.setDistrict(cep.getBairro());
        address.setCity(cep.getLocalidade());
        address.setState(cep.getUf());
        address.setCep(cep.getCep());

        orderToCreate.setCpf(request.getCpf());
        // orderToCreate.setItems();
        orderToCreate.setTotal(request.getTotal());
        orderToCreate.setAddress(address);

        OrderEntity orderCreated = orderRepository.save(orderToCreate);

        return mapper.map(orderCreated, OrderResponseDTO.class);
    }
    
}
