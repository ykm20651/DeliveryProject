package com.example.deliveryproject.Repository;

import com.example.deliveryproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
