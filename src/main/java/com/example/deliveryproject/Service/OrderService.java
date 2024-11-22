package com.example.deliveryproject.Service;

import com.example.deliveryproject.Dto.OrderRequestDTO;
import com.example.deliveryproject.Dto.OrderResponseDTO;
import com.example.deliveryproject.Entity.Food;
import com.example.deliveryproject.Entity.Order;
import com.example.deliveryproject.Entity.User;
import com.example.deliveryproject.Repository.FoodRepository;
import com.example.deliveryproject.Repository.OrderRepository;
import com.example.deliveryproject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
    }

    // 모든 주문 조회
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getUser().getName(),
                        order.getFood().getName(),
                        order.getQuantity(),
                        order.getStatus()))
                .collect(Collectors.toList());
    }

    // 주문 추가
    @Transactional
    public OrderResponseDTO addOrder(OrderRequestDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Food food = foodRepository.findById(request.foodId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        Order order = new Order(user, food, request.quantity(), "Ordered");
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(
                savedOrder.getId(),
                user.getName(),
                food.getName(),
                savedOrder.getQuantity(),
                savedOrder.getStatus());
    }

    // 주문 수정
    @Transactional
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Food food = foodRepository.findById(request.foodId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        // 기존 주문의 필드를 새로운 값으로 업데이트
        Order updatedOrder = new Order(
                user,
                food,
                request.quantity(),
                "Updated"
        );
        updatedOrder = orderRepository.save(updatedOrder);

        return new OrderResponseDTO(
                updatedOrder.getId(),
                updatedOrder.getUser().getName(),
                updatedOrder.getFood().getName(),
                updatedOrder.getQuantity(),
                updatedOrder.getStatus());
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
