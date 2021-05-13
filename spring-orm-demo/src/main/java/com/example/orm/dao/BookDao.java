package com.example.orm.dao;

import com.example.orm.core.ORMConfig;
import com.example.orm.core.ORMSession;
import com.example.orm.entity.Book;
import org.junit.Test;

public class BookDao {
    private ORMConfig config;
    @Test
    public void testSave() throws Exception {
        //1.创建ORMConfig对象
        config = new ORMConfig();
        //2.创建ORMSession对象
        ORMSession session = config.buildORMSession();
        //3.创建实体类对象并保存
        Book book = new Book();
        book.setId(288);
        book.setName("spring-boot");
        book.setAuthor("李清照");
        book.setPrice(98.5);
//        session.save(book);
//        session.delete(book);
        Book one = (Book)session.findOne(Book.class, 188);
        System.out.println(one);
        //4.释放资源
        session.close();
    }
}
