package com.example.deliveryproject.Service;

import com.example.deliveryproject.Dto.LoginRequestDTO;
import com.example.deliveryproject.Dto.UserRequestDTO;
import com.example.deliveryproject.Dto.UserResponseDTO;
import com.example.deliveryproject.Entity.User;
import com.example.deliveryproject.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 추가
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        // 요청 DTO에서 값을 가져와 User 엔티티 생성
        User user = new User(userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.password(), userRequestDTO.phoneNumber());
        // User 엔티티를 저장
        User savedUser = userRepository.save(user);
        // 저장된 엔티티를 응답 DTO로 변환 -> 사용자 이메일과 패스워드는 반환할 이유가 없지 않나? 보안상..
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getPhoneNumber());
    }

    // 모든 회원 조회
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    // 회원가입
    public void registerUser(UserRequestDTO userRequestDTO) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(userRequestDTO.email())) {
            System.out.println("Email already exists");
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        //디버깅 중
        System.out.println("Email does not exist, proceeding...");

        // 비밀번호 암호화
        System.out.println("Encrypting password...");
        String encryptedPassword = passwordEncoder.encode(userRequestDTO.password());
        System.out.println("Encrypted password: " + encryptedPassword);

        // User 엔티티 생성 및 저장
        User user = new User(userRequestDTO.name(), userRequestDTO.email(), encryptedPassword, userRequestDTO.phoneNumber());
        System.out.println("User created: " + user);

        userRepository.save(user);
        System.out.println("User saved to repository.");
    }


    public void loginUser(LoginRequestDTO loginRequestDTO, HttpSession session) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 올바르지 않습니다."); // 또는 커스텀 예외를 던짐
        }


        // 세션에 사용자 정보 저장
        session.setAttribute("user", user);
    }





}
