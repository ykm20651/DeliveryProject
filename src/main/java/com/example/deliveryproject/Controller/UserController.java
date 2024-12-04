package com.example.deliveryproject.Controller;

import com.example.deliveryproject.Dto.FoodDTO;
import com.example.deliveryproject.Dto.LoginRequestDTO;
import com.example.deliveryproject.Dto.UserRequestDTO;
import com.example.deliveryproject.Dto.UserResponseDTO;
import com.example.deliveryproject.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO signRequestDTO) {
        try {
            userService.registerUser(signRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registration successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        try {
            userService.loginUser(loginRequestDTO, session);
            return ResponseEntity.ok("로그인 성공!");
        } catch (IllegalArgumentException e) {
            // 예외를 401 상태 코드와 함께 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("Logout successful!");
    }





}
