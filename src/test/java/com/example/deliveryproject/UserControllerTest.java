package com.example.deliveryproject;

import com.example.deliveryproject.Controller.UserController;
import com.example.deliveryproject.Dto.LoginRequestDTO;
import com.example.deliveryproject.Dto.UserRequestDTO;
import com.example.deliveryproject.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = true)
@DisplayName("UserController 단위 테스트")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("registerUser API 테스트: 회원가입")
    public void testRegisterUser() throws Exception {
        // Given
        UserRequestDTO requestDTO = new UserRequestDTO(
                "testRegisterUser",
                "testRegisterUser@gmail.com",
                "testRegisterUser303030",
                "010-0000-0006"
        );
        doNothing().when(userService).registerUser(any(UserRequestDTO.class));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/home/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()) // CSRF 토큰 추가
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("회원가입 성공!"));
    }

    @Test
    @DisplayName("loginUser API 테스트: 로그인")
    public void testLoginUser() throws Exception {
        // Given
        LoginRequestDTO requestDTO = new LoginRequestDTO("testLoginUser@gmail.com", "testLoginUser335");
        doNothing().when(userService).loginUser(any(LoginRequestDTO.class), any());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/home/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()) // CSRF 토큰 추가
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 성공!"));
    }

    @Test
    @DisplayName("logoutUser API 테스트: 로그아웃")
    public void testLogoutUser() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/home/users/logout")
                        .with(csrf())) // CSRF 토큰 추가
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 성공!"));
    }
}
