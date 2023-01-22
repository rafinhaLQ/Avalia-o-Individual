package uol.compass.ms.order.framework.adpater.in.rest;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uol.compass.ms.order.application.port.in.ItemService;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.response.ItemResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDTO> create(@RequestBody @Valid ItemRequestDTO request) {
        ItemResponseDTO response = itemService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponseDTO>> findAll(Pageable pageable) {
        Page<ItemResponseDTO> response = itemService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
