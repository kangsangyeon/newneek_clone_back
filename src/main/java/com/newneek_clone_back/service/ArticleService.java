package com.newneek_clone_back.service;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.entity.ArticleCategory;
import com.newneek_clone_back.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Article> findAllByOrderByCreatedAtDesc() {
        return articleRepository.findAllByOrderByCreatedAtDesc().orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Article> findAllByCategoryOrderByCreatedAtDesc(ArticleCategory category) {
        return articleRepository.findAllByCategoryOrderByCreatedAtDesc(category).orElseThrow(() -> new IllegalArgumentException());
    }

    public Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Page<Article> findAllByCategoryOrderByCreatedAtDesc(ArticleCategory category, Pageable pageable) {
        return articleRepository.findAllByCategoryOrderByCreatedAtDesc(category, pageable);
    }
}
