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
public class ArticleSummaryResponseDto {
    private final int MAX_CONTENTS_LENGTH = 40;

    private Long id;

    private String title;

    private String image;

    private String contents;

    private String categoryName;

    public ArticleSummaryResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.image = article.getImage();
        this.categoryName = article.getCategory().getName();

        // 본문이 글자 수 제한을 넘기지 않도록 합니다.
        if (article.getContents().length() > MAX_CONTENTS_LENGTH) {
            this.contents = article.getContents().substring(0, MAX_CONTENTS_LENGTH);
            this.contents += "⋯";
        } else {
            this.contents = article.getContents();
        }
    }

}
