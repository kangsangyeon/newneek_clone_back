package com.newneek_clone_back.dto;

import com.newneek_clone_back.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSummaryResponseDto {
    private final int MAX_CONTENTS_LENGTH = 40;

    private LocalDateTime createdAt;

    private Long id;

    private String title;

    private String image;

    private String contents;

    private String categoryName;

    public ArticleSummaryResponseDto(Article article) {
        this.createdAt = article.getCreatedAt();
        this.id = article.getId();
        this.title = article.getTitle();
        this.image = article.getImage();
        this.categoryName = article.getCategory().getName();
        this.contents = "";

        // 가장 처음에 나오는 p태그 안의 내용을 보여줍니다.
        Document doc = Jsoup.parse(article.getContents());
        Element firstPTag = doc.selectFirst("p:nth-child(1)");
        if (firstPTag == null)
            return;

        String contentsWithoutTags = firstPTag.text();

        // 본문이 글자 수 제한을 넘기지 않도록 합니다.
        if (article.getContents().length() > MAX_CONTENTS_LENGTH) {
            this.contents = contentsWithoutTags.substring(0, MAX_CONTENTS_LENGTH);
            this.contents += "⋯";
        } else {
            this.contents = contentsWithoutTags;
        }
    }

}
