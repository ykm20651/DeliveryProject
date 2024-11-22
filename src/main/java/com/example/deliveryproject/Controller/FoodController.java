package com.example.deliveryproject.Controller;

import com.example.deliveryproject.Dto.FoodDTO;
import com.example.deliveryproject.Service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/foods")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public FoodDTO addFood(@RequestBody FoodDTO foodDTO) {
        return foodService.addFood(foodDTO);
    }
    @GetMapping
    public List<FoodDTO> getAllFoods() {
        return foodService.getAllFoods();
    }
}
