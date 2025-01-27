package com.example.deliveryproject;

import com.example.deliveryproject.Dto.LoginRequestDTO;
import com.example.deliveryproject.Dto.UserRequestDTO;
import com.example.deliveryproject.Dto.UserResponseDTO;
import com.example.deliveryproject.Entity.User;
import com.example.deliveryproject.Repository.UserRepository;
import com.example.deliveryproject.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 단위 테스트")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    @DisplayName("addUser 메서드 테스트: 사용자 추가")
    public void testAddUser() {
        // Given
        UserRequestDTO requestDTO = new UserRequestDTO("testAddUser",
                "testAddUser@gmail.com", "testAddUser3030", "010-0000-0010");
        User mockUser = User.builder()
                .name("testAddUser")
                .email("testAddUser@gmail.com")
                .password("testAddUser3030")
                .phoneNumber("010-0000-0010")
                .build();

        // 저장 후 반환되는 User 객체 Mock 설정
        when(userRepository.save(any(User.class))).thenReturn(
                User.builder()
                        .name("testAddUser")
                        .email("testAddUser@gmail.com")
                        .password("testAddUser3030")
                        .phoneNumber("010-0000-0010")
                        .build()
        );

        // When
        UserResponseDTO responseDTO = userService.addUser(requestDTO);

        // Then
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.name()).isEqualTo("testAddUser");
        assertThat(responseDTO.phoneNumber()).isEqualTo("010-0000-0010");

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    @DisplayName("getAllUsers 메서드 테스트: 모든 사용자 조회")
    public void testGetAllUsers() {
        // Given
        User user1 = User.builder()
                .name("testGetAllUsers1")
                .email("testGetAllUsers1@gmail.com")
                .password("testGetAllUsers111")
                .phoneNumber("010-0000-0011")
                .build();

        User user2 = User.builder()
                .name("testGetAllUsers2")
                .email("testGetAllUsers2@gmail.com")
                .password("testGetAllUsers222")
                .phoneNumber("010-0000-0012")
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<UserResponseDTO> responseDTOs = userService.getAllUsers();

        // Then
        assertThat(responseDTOs).hasSize(2);
        assertThat(responseDTOs.get(0).name()).isEqualTo("testGetAllUsers1");
        assertThat(responseDTOs.get(1).name()).isEqualTo("testGetAllUsers2");

        verify(userRepository, times(1)).findAll();
    }

    //결과 : userRepository.save()메소드 호출 오류로 인한 테스트 실패. . .
    @Test
    @DisplayName("registerUser 메서드 테스트: 회원가입")
    public void testRegisterUser() {
        // Given
        UserRequestDTO requestDTO = new UserRequestDTO("testRegisterUser",
                "testRegisterUser@gmail.com", "testRegisterUser66", "010-0000-0013");

        // Mock 설정
        when(userRepository.existsByEmail(anyString())).thenReturn(false); // 이메일 중복 체크
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword"); // 비밀번호 암호화
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0)); // save 호출 시 같은 객체 반환

        // When
        userService.registerUser(requestDTO);

        // Then
        // save() 호출 확인
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        // 캡처된 User 객체 검증
        assertThat(capturedUser.getName()).isEqualTo("testRegisterUser");
        assertThat(capturedUser.getPassword()).isEqualTo("encryptedPassword");
        assertThat(capturedUser.getEmail()).isEqualTo("testRegisterUser@gmail.com");
        assertThat(capturedUser.getPhoneNumber()).isEqualTo("010-0000-0013");
    }


    //결과 : 등록되지 않은 이메일로 인한 테스트 실패
    @Test
    @DisplayName("loginUser 메서드 테스트: 로그인 성공")
    public void testLoginUserSuccess() {
        // Given
        LoginRequestDTO requestDTO = new LoginRequestDTO("testLoginUserSuccess@gmail.com", "testLoginUserSuccess55");
        User mockUser = User.builder()
                .name("testLoginUserSuccess")
                .email("testLoginUserSuccess@gmail.com")
                .password("$2a$10$abcdefghijklmnopqrstuv") // 실제 Bcrypt 형식의 패스워드 (더미)
                .phoneNumber("010-0000-0014")
                .build();

        when(userRepository.findByEmail("testLoginUserSuccess@gmail.com")).thenReturn(Optional.of(mockUser)); // 정확한 값 사용
        when(passwordEncoder.matches("testLoginUserSuccess55", mockUser.getPassword())).thenReturn(true); // matches 동작 Mock

        HttpSession mockSession = mock(HttpSession.class);

        // When
        userService.loginUser(requestDTO, mockSession);

        // Then
        verify(mockSession, times(1)).setAttribute(eq("user"), any(User.class)); // 세션에 저장 검증
    }

}
