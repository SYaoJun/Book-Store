package com.bookstore.repository;


import com.bookstore.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author yaojun
 * @create 2021-01-25 17:10
 */
public interface UserRepository extends JpaRepository<Info, Integer> {
    List<Info> findByName(String name);
}
