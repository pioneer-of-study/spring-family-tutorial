package entity;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "t_book")
public class Book {
    @Id
    @Column(name = "bid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bname")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private double price;
}
