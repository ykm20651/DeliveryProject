package com.example.deliveryproject.Repository;

import com.example.deliveryproject.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {}
