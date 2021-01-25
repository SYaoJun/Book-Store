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
public class BookEsController {
    @Autowired
    private BookService bookService;
    /**
     *根据搜索内容查询相关的书籍
     */
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
    /**
     *查询全部书籍
     */
    @GetMapping("/bookQueryAll")
    public List bookQueryAll() throws Exception {
        log.info("进入了bookQueryAll");
        List list = bookService.bookQueryAll();
        return list;
    }
}
