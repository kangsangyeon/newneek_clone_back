package com.newneek_clone_back.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor//자동생성자
public class Article extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private ArticleCategory category;
        this.title = title;
        this.image = image;
        this.contents = contents;
    }
}
