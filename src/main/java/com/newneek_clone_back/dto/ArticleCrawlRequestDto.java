package com.newneek_clone_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCrawlRequestDto {
    private ArticleRequestDto requestDto;

    private LocalDate crawledCreatedAt;
}
