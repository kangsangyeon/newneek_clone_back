package com.newneek_clone_back.service;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.dto.ArticleSummaryResponseDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.entity.ArticleCategory;
import com.newneek_clone_back.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final int PAGE_SIZE = 12;

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

    public List<Article> findAllByCategoryOrderByCreatedAtDesc(String categoryName) {
        ArticleCategory category = categoryService.findByName(categoryName);
        return articleRepository.findAllByCategoryOrderByCreatedAtDesc(category).orElseThrow(() -> new IllegalArgumentException());
    }

    public Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Page<Article> findAllByCategoryOrderByCreatedAtDesc(String categoryName, Pageable pageable) {
        ArticleCategory category = categoryService.findByName(categoryName);
        return articleRepository.findAllByCategoryOrderByCreatedAtDesc(category, pageable);
    }

    public List<ArticleSummaryResponseDto> getArticleSummuryList(String categoryName, Integer page) {
        List<Article> articleList;

        if (page == null) {
            articleList = categoryName == null ? findAllByOrderByCreatedAtDesc() : findAllByCategoryOrderByCreatedAtDesc(categoryName);
        } else {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE);

            Page<Article> articlePage = categoryName == null ? findAllByOrderByCreatedAtDesc(pageable) : findAllByCategoryOrderByCreatedAtDesc(categoryName, pageable);
            articleList = articlePage.getContent();
        }

        List<ArticleSummaryResponseDto> articleSummaryList = new ArrayList<>(articleList.size());
        articleList.forEach(article -> articleSummaryList.add(new ArticleSummaryResponseDto(article)));

        return articleSummaryList;
    }
}
