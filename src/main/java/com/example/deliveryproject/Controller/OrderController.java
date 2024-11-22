package com.example.deliveryproject.Controller;

import com.example.deliveryproject.Dto.OrderRequestDTO;
import com.example.deliveryproject.Dto.OrderResponseDTO;
import com.example.deliveryproject.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home") // 기본 경로를 /home으로 설정
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 리스트 조회
    @GetMapping("/orders")
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders(); // 모든 주문 조회 후 JSON으로 반환
    }

    // 주문 추가
    @PostMapping("/orders")
    public OrderResponseDTO addOrder(@RequestBody OrderRequestDTO request) {
        return orderService.addOrder(request); // 주문 추가
    }

    // 주문 수정
    @PutMapping("/orders/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request); // 주문 수정
    }

    // 주문 취소
    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order canceled";
    }
}
