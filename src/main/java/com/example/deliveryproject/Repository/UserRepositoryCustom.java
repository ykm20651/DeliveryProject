package com.example.deliveryproject.Repository;

import com.example.deliveryproject.Entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllUsersWithOrders();

}
