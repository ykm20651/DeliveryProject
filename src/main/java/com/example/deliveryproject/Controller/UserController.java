package com.example.deliveryproject.Controller;

import com.example.deliveryproject.Dto.FoodDTO;
import com.example.deliveryproject.Dto.UserRequestDTO;
import com.example.deliveryproject.Dto.UserResponseDTO;
import com.example.deliveryproject.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO addUser(@RequestBody UserRequestDTO userDto) {
        System.out.println("User DTO received: " + userDto);
        //서비스 게층에 DTO 객체 그대로 전달
        return userService.addUser(userDto);
    }
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
