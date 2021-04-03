package com.newneek_clone_back.service;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final ArticleCategoryService categoryService;

    public Article create(ArticleRequestDto requestDto) {
        Article newArticle = new Article(requestDto, categoryService);
        articleRepository.save(newArticle);

        return newArticle;
    }
}
