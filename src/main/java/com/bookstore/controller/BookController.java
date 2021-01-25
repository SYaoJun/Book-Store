package com.bookstore.controller;

import com.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaojun
 * @create 2021-01-25 14:21
 */
@RestController
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/bookQuery/{keyword}")
    public List queryBook(@PathVariable(name = "keyword") String keyword) throws Exception {
        /*当查询为空时返回全部书籍信息*/
        if (keyword == null){
            return bookService.bookQueryAll();
        }
        log.info("进入了Controller:[{}]", keyword);
        List list = bookService.queryDocument(keyword);
        return list;
    }
    @GetMapping("/booktest")
    public String booktest(){
        log.info("进入了booktest");
        return "hello world!";
    }
    @GetMapping("/bookQueryAll")
    public List bookQueryAll() throws Exception {
        log.info("进入了bookQueryAll");
        List list = bookService.bookQueryAll();
        return list;
    }
}
