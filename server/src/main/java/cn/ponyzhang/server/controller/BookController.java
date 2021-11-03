package cn.ponyzhang.server.controller;

import cn.ponyzhang.server.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


    @RequestMapping(value = "info/{bookNum}/{bookName}",method = RequestMethod.GET)
    public Book info(@PathVariable("bookNum") Integer bookNum,@PathVariable("bookName") String bookName){
        Book book = new Book();
        book.setBookNum(bookNum);
        book.setBookName(bookName);
        return book;
    }
}
