package com.example.deliveryproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private String status;

    private String menuName;  // menu_name과 매핑
    private Double menuPrice; // menu_price와 매핑

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    protected Order() {}

    public Order(User user, Food food, Integer quantity, String status) {
        this.user = user;
        this.food = food;
        this.quantity = quantity;
        this.status = status;
        this.menuName = food.getName(); // Food의 이름 가져오기
        this.menuPrice = food.getPrice(); // Food의 가격 가져오기
    }

    public Long getId() { return id; }
    public Integer getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public String getMenuName() { return menuName; }
    public Double getMenuPrice() { return menuPrice; }
    public User getUser() { return user; }
    public Food getFood() { return food; }
}
