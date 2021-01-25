package com.bookstore.service;


import com.bookstore.entity.Info;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaojun
 * @create 2020-12-12 17:36
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Info findById(int id){
        return userRepository.getOne(id);
    }
    public List<Info> findByName(String name){
        return userRepository.findByName(name);
    }
}
