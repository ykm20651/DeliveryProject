package com.example.deliveryproject.Repository;

import com.example.deliveryproject.Entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.deliveryproject.Entity.QFood.food;
import static com.example.deliveryproject.Entity.QOrder.order;
import static com.example.deliveryproject.Entity.QUser.user;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<User> findAllUsersWithOrders() {
        List<User> users = queryFactory
                .selectFrom(user).distinct()
                .leftJoin(user.orders, order).fetchJoin()
                .leftJoin(order.food, food).fetchJoin()
                .fetch();

        System.out.println("조회된 모든 사용자 수 : " + users.size());
        return users;
    }
}
