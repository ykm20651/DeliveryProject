package com.example.deliveryproject.Repository;

import com.example.deliveryproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    boolean existsByEmail(String email); //DB에 중복 이메일있는지 검사
    Optional<User> findByEmail(String email); //로그인 시 DB에서 이메일 검사
}
