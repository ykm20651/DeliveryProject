package com.example.deliveryproject.Service;

import com.example.deliveryproject.Dto.FoodDTO;
import com.example.deliveryproject.Entity.Food;
import com.example.deliveryproject.Repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public FoodDTO addFood(FoodDTO foodDTO) {
        Food food = new Food(foodDTO.name(), foodDTO.price()); // 필드 이름으로 접근
        Food savedFood = foodRepository.save(food);
        return new FoodDTO(savedFood.getId(), savedFood.getName(), savedFood.getPrice());
    }

    // 모든 음식 데이터를 반환하는 메서드
    public List<FoodDTO> getAllFoods() {
        return foodRepository.findAll() // JpaRepository의 기본 메서드 사용
                .stream() // List<Food>를 스트림으로 변환
                .map(food -> new FoodDTO(food.getId(), food.getName(), food.getPrice())) // Food를 DTO로 변환
                .toList(); // 스트림 결과를 List로 변환
    }
}
