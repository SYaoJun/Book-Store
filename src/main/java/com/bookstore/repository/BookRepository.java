package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yaojun
 * @create 2021-01-15 17:59
 */

public interface BookRepository extends JpaRepository<Book, Integer> {

}
