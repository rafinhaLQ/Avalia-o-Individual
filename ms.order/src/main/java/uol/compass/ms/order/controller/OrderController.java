package uol.compass.ms.order.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.request.OrderRequestDTO;
import uol.compass.ms.order.model.dto.response.OrderResponseDTO;
import uol.compass.ms.order.service.impl.OrderServiceImpl;

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

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> findAll(@RequestParam(required = false) String cpf, Pageable pageable) {
        Page<OrderResponseDTO> response = orderService.findAll(cpf, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") Long id) {
        OrderResponseDTO response = orderService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
