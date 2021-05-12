package com.example.orm.entity;

import com.example.orm.annotation.ORMColumn;
import com.example.orm.annotation.ORMId;
import com.example.orm.annotation.ORMTable;
import lombok.Data;

@Data
@ORMTable(name = "t_book")
public class Book {
    @ORMId
    @ORMColumn(name = "bid")
    private Integer id;

    @ORMColumn(name = "bname")
    private String name;

    @ORMColumn(name = "author")
    private String author;

    @ORMColumn(name = "price")
    private double price;
}
