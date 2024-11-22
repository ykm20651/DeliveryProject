package com.example.deliveryproject.Service;

import com.example.deliveryproject.Dto.UserRequestDTO;
import com.example.deliveryproject.Dto.UserResponseDTO;
import com.example.deliveryproject.Entity.User;
import com.example.deliveryproject.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        // 요청 DTO에서 값을 가져와 User 엔티티 생성
        User user = new User(userRequestDTO.name(), userRequestDTO.phoneNumber());
        // User 엔티티를 저장
        User savedUser = userRepository.save(user);

        // 저장된 엔티티를 응답 DTO로 변환
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getPhoneNumber());
    }
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getPhoneNumber()))
                .collect(Collectors.toList());
    }
}
