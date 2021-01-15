package com.bookstore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author yaojun
 * @create 2021-01-15 17:48
 */
@Entity
@Data
public class Book {
    @Id
    private Integer id;
    private String Name;
    private String author;
}
