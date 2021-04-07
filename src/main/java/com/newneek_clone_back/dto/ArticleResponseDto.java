package com.newneek_clone_back.dto;

import com.newneek_clone_back.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private String createdAt;

    private Long id;

    private String title;

    private String image;

    private String contents;

    private String categoryName;

    public ArticleResponseDto(Article article) {
        if (article.getCrawledCreatedAt() != null) {
            this.createdAt = article.getCrawledCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } else {
            this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        }

        this.id = article.getId();
        this.title = article.getTitle();
        this.image = article.getImage();
        this.categoryName = article.getCategory().getName();
        this.contents = article.getContents();
    }
}
