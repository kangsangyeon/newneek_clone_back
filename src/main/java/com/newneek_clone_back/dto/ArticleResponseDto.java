package com.newneek_clone_back.dto;

import com.newneek_clone_back.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id;

    private String title;

    private String image;

    private String contents;

    private String categoryName;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.image = article.getImage();
        this.categoryName = article.getCategory().getName();

        this.contents = article.getContents();
    }
}
