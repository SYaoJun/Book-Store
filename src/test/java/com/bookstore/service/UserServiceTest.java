package com.bookstore.service;

import com.bookstore.entity.Info;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yaojun
 * @create 2021-01-25 18:18
 */
@SpringBootTest

class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void findByName() {
        List<Info> infoList = userRepository.findByName("mysql");
        infoList.forEach(e-> System.out.println(e));
    }
}
