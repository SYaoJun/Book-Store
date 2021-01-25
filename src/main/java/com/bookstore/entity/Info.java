package com.bookstore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yaojun
 * @create 2020-12-08 17:26
 */
@Data
@NoArgsConstructor
@Entity(name = "info")
public class Info {
    @Id
    private int id;
    private String name;
    private int pwd;

    public Info(int id, String name, int pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }
}
