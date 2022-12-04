package com.example.file.service;


import com.example.file.entity.QUsers;
import com.example.file.entity.Users;
import com.example.file.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserService {

    private final EntityManager entityManager;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }



    public Users save(String name, String phone) {
        Users user  = new Users();
        user.setName(name);
        user.setPhone(phone);
        user.setCreateDate(LocalDateTime.now());

       return userRepository.save(user);
    }

    public Optional<Users> get(Long userId) {
        return userRepository.findById(userId);
    }

    public Page<Users> get(String keyword, Integer page, Integer size) {

        var query = new JPAQuery<Users>(entityManager);
        var pageRequest = PageRequest.of(page - 1, size);
        setQueryCondition(query, keyword);
        var users = query.limit(pageRequest.getPageSize()).offset(pageRequest.getOffset()).fetch();
        var count = query.fetchCount();

        return new PageImpl<>(users, pageRequest, count);
    }

    private void setQueryCondition(JPAQuery<Users> query, String keyword) {
        query.select(QUsers.users).from(QUsers.users);
        if (StringUtils.isNoneBlank(keyword)) {
            query.where(QUsers.users.name.eq(keyword));
        }
    }
}
