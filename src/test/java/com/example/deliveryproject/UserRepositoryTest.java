package com.example.deliveryproject;

import com.example.deliveryproject.Config.QuerydslConfiguration;
import com.example.deliveryproject.Entity.Food;
import com.example.deliveryproject.Entity.Order;
import com.example.deliveryproject.Entity.User;
import com.example.deliveryproject.Repository.FoodRepository;
import com.example.deliveryproject.Repository.OrderRepository;
import com.example.deliveryproject.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfiguration.class) // for registering 'class attached @Configuration'
@Transactional
@DisplayName("UserRepository 단위 테스트")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(readOnly = true)
    @DisplayName("FindAllUsersWithOrders 테스트 : 모든 사용자와 주문을 QueryDSL 적용 - 한 번의 쿼리로 가져오기")
    public void testFindAllUsersWithOrders(){
        //Given
        //1. 사용자 데이터 추가
        User user1 = User.builder()
                .email("test1@gmail.com")
                .name("QueryDSL1")
                .password("QueryDSL11!!")
                .phoneNumber("010-1111-1111")
                .build();

        User user2 = User.builder()
                .email("test2@gmail.com")
                .name("QueryDSL2")
                .password("QueryDSL22@@")
                .phoneNumber("010-2222-2222")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //2. 음식 데이터 추가
        Food food1 = Food.builder()
                .name("라스베가스 소금절이 감자튀김")
                .price(45000.0)
                .build();

        Food food2 = Food.builder()
                .name("브런치 세트")
                .price(40000.0)
                .build();
        foodRepository.save(food1);
        foodRepository.save(food2);

        //3. 주문 데이터 추가

        Order order1 = Order.builder()
                .user(user1)
                .food(food1)
                .quantity(3)
                .build();
        Order order2 = Order.builder()
                .user(user1)
                .food(food2)
                .quantity(1)
                .build();

        Order order3 = Order.builder()
                .user(user2)
                .food(food2)
                .quantity(5)
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        //When
        List<User> users = userRepository.findAllUsersWithOrders();

        //Then - user 조회 후, 주문 갯수 검증
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getOrders()).hasSize(2);
        assertThat(users.get(1).getOrders()).hasSize(1);


    }


    @Test
    @DisplayName("save 메서드 테스트: 사용자 정보를 데이터베이스에 저장.")
    public void testSaveUser() {
        // Given
        User user = User.builder()
                .email("testSaveUser@gmail.com")
                .name("testSaveUser")
                .password("testSaveUser00")
                .phoneNumber("010-0000-0001") // 필수 필드 추가
                .build();

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("testSaveUser@gmail.com");
        assertThat(savedUser.getPhoneNumber()).isEqualTo("010-0000-0001");
    }

    @Test
    @DisplayName("findById 메서드 테스트: ID로 사용자 정보를 조회.")
    public void testFindById() {
        // Given
        User user = User.builder()
                .email("testFindById@gmail.com")
                .name("testFindById")
                .password("testFindById11")
                .phoneNumber("010-0000-0002") // 필수 필드 추가
                .build();
        User savedUser = userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("testFindById@gmail.com");
        assertThat(foundUser.get().getPhoneNumber()).isEqualTo("010-0000-0002");
    }

    @Test
    @DisplayName("findAll 메서드 테스트: 모든 사용자 정보를 조회.")
    public void testFindAllUsers() {
        // Given
        User user1 = User.builder()
                .email("testFindAllUsers33@gmail.com")
                .name("testFindAllUsers33")
                .password("testFindAllUsers33")
                .phoneNumber("010-0000-0003") // 필수 필드 추가
                .build();

        User user2 = User.builder()
                .email("testFindAllUsers44@gmail.com")
                .name("testFindAllUsers44")
                .password("FindAllPassword44")
                .phoneNumber("010-0000-0004") // 필수 필드 추가
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getEmail()).isEqualTo("testFindAllUsers33@gmail.com");
        assertThat(users.get(1).getEmail()).isEqualTo("testFindAllUsers44@gmail.com");
        assertThat(users.get(0).getPhoneNumber()).isEqualTo("010-0000-0003");
        assertThat(users.get(1).getPhoneNumber()).isEqualTo("010-0000-0004");
    }

    @Test
    @DisplayName("deleteById 메서드 테스트: ID로 사용자 정보를 삭제.")
    public void testDeleteUser() {
        // Given
        User user = User.builder()
                .email("testDeleteUser@gmail.com")
                .name("testDeleteUser")
                .password("testDeleteUser55")
                .phoneNumber("010-0000-0005")
                .build();
        User savedUser = userRepository.save(user);

        // When
        userRepository.deleteById(savedUser.getId());

        // Then
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isEmpty();
    }
}
