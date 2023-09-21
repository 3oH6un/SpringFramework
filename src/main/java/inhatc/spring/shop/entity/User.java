package inhatc.spring.shop.entity;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "used_id")
    private long id;

    @Column(nullable = false)
    private int age;

    @Column(length = 30, nullable = false)
    private String name;
}
