package com.example.deliveryproject.Dto;

public record OrderRequestDTO(Long userId, Long foodId, String foodName, Integer quantity) {}
