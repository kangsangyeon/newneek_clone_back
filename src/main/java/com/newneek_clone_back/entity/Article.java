package com.newneek_clone_back.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor//자동생성자
public class Article extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private  String title;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    private String contents;


}
