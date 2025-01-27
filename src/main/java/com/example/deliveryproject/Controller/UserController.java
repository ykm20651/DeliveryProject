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
import java.util.Map;

@RestController
@RequestMapping("/home/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            userService.registerUser(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO requestDTO, HttpSession session) {
        try {
            userService.loginUser(requestDTO, session);
            return ResponseEntity.ok("로그인 성공!");
        } catch (IllegalArgumentException e) { // 비밀번호 또는 이메일 오류 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) { // 비밀번호 불일치 또는 기타 오류
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate(); //세션 닫기
        return ResponseEntity.ok("로그아웃 성공!");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getLoggedInUser(HttpSession session) {
        // 세션에서 "user"라는 키로 저장된 정보를 꺼냄
        UserResponseDTO user = (UserResponseDTO) session.getAttribute("user");

        // 세션에 "user"가 없으면 (즉, 로그인하지 않은 상태면)
        if (user == null) {
            // 401 Unauthorized 응답 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 세션에 "user"가 있으면 해당 정보를 클라이언트로 반환
        return ResponseEntity.ok(user);
    }

}
