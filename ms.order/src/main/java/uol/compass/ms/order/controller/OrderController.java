package uol.compass.ms.order.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.OrderRequestDTO;
import uol.compass.ms.order.model.dto.OrderResponseDTO;
import uol.compass.ms.order.service.OrderServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class OrderController {
    
    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO request) {
        OrderResponseDTO response = orderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
