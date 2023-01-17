package uol.compass.mshistory.framework.adpater.in.rest;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uol.compass.mshistory.application.service.OrderHistoryServiceImpl;
import uol.compass.mshistory.domain.dto.response.OrderHistoryResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historico")
public class OrderHistoryController {

    private final OrderHistoryServiceImpl historyService;

    @GetMapping
    public ResponseEntity<Page<OrderHistoryResponseDTO>> findAll(
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
        Pageable pageable
    ) {
        Page<OrderHistoryResponseDTO> response = historyService.findAll(startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
