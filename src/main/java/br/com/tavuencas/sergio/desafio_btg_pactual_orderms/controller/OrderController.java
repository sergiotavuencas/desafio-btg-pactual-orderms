package br.com.tavuencas.sergio.desafio_btg_pactual_orderms.controller;

import br.com.tavuencas.sergio.desafio_btg_pactual_orderms.controller.dto.ApiResponse;
import br.com.tavuencas.sergio.desafio_btg_pactual_orderms.controller.dto.PaginationResponse;
import br.com.tavuencas.sergio.desafio_btg_pactual_orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
            @PathVariable("customerId") Long customerId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
            ) {
        var pageResponse = service.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOnOrders = service.findTotalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
