package com.newneek_clone_back.service;

import com.newneek_clone_back.delegate.IArticleUpdateDelegate;
import com.newneek_clone_back.dto.ArticleCrawlRequestDto;
import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.dto.ArticleSummaryResponseDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.entity.ArticleCategory;
import com.newneek_clone_back.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final int PAGE_SIZE = 12;
    private final int TITLE_CONTAINING_KEYWORDS_SCORE = 5;
    private final int CONTENTS_CONTAINING_KEYWORDS_SCORE = 5;

    private final ArticleRepository articleRepository;

    private final ArticleCategoryService categoryService;

    public Article create(ArticleRequestDto requestDto) {
        Article newArticle = new Article(requestDto, categoryService);
        articleRepository.save(newArticle);

        return newArticle;
    }

    public Article create(ArticleCrawlRequestDto requestDto) {
        Article newArticle = new Article(requestDto, categoryService);
        articleRepository.save(newArticle);

        return newArticle;
    }

    @Transactional
    public void update(Article article, ArticleRequestDto requestDto) {
        article.update(requestDto, categoryService);
    }

    @Transactional
    public void update(Article article, IArticleUpdateDelegate delegate) {
        delegate.update(article);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Article> findAllByOrderByCrawledCreatedAtDesc() {
        return articleRepository.findAllByOrderByCrawledCreatedAtDesc().orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Article> findAllByCategoryOrderByCrawledCreatedAtDesc(String categoryName) {
        ArticleCategory category = categoryService.findByName(categoryName);
        return articleRepository.findAllByCategoryOrderByCrawledCreatedAtDesc(category).orElseThrow(() -> new IllegalArgumentException());
    }

    public Page<Article> findAllByOrderByCrawledCreatedAtDesc(Pageable pageable) {
        return articleRepository.findAllByOrderByCrawledCreatedAtDesc(pageable);
    }

    public Page<Article> findAllByCategoryOrderByCrawledCreatedAtDesc(String categoryName, Pageable pageable) {
        ArticleCategory category = categoryService.findByName(categoryName);
        return articleRepository.findAllByCategoryOrderByCrawledCreatedAtDesc(category, pageable);
    }

    public List<ArticleSummaryResponseDto> getArticleSummuryList(String categoryName, Integer page) {
        List<Article> articleList;

        if (page == null) {
            articleList = categoryName == null ? findAllByOrderByCrawledCreatedAtDesc() : findAllByCategoryOrderByCrawledCreatedAtDesc(categoryName);
        } else {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE);

            Page<Article> articlePage = categoryName == null ? findAllByOrderByCrawledCreatedAtDesc(pageable) : findAllByCategoryOrderByCrawledCreatedAtDesc(categoryName, pageable);
            articleList = articlePage.getContent();
        }

        List<ArticleSummaryResponseDto> articleSummaryList = new ArrayList<>(articleList.size());
        articleList.forEach(article -> articleSummaryList.add(new ArticleSummaryResponseDto(article)));

        return articleSummaryList;
    }

    public List<ArticleSummaryResponseDto> getRelativeArticleSummaryList(ArticleCategory category) {
        List<Article> relativeArticleList = articleRepository.findTop4ByCategoryOrderByCrawledCreatedAtDesc(category);

        List<ArticleSummaryResponseDto> relativeArticleSummaryList = new ArrayList<>(relativeArticleList.size());
        relativeArticleList.forEach(article -> relativeArticleSummaryList.add(new ArticleSummaryResponseDto(article)));

        return relativeArticleSummaryList;
    }

    public List<ArticleSummaryResponseDto> getArticleSummaryListUsingKeywords(String keywords) {

        @Getter
        @AllArgsConstructor
        class ArticleAndScorePair implements Comparable<ArticleAndScorePair> {
            private final Article article;
            private final Integer score;

            @Override
            public int compareTo(ArticleAndScorePair o) {
                return this.score - o.score;
            }
        }

        // 사용자가 요청한 검색어를 keywords라고 합니다.
        // keywords를 공백 단위로 쪼갠 것을 keyword라고 합니다.
        // "제목"이나 "내용"중에 어느 곳이라도 포함되어 있는 Article들의 목록을 조회합니다.
        String[] keywordsSplitted = keywords.split(" ");
        Set<Article> containingAnyKeywordsArticleSet = new HashSet<>();
        for (String keyword : keywordsSplitted) {
            List<Article> containingKeywordArticleList = articleRepository.findAllByTitleContainingOrContentsContainingOrderByCrawledCreatedAtDesc(keyword, keyword);
            containingAnyKeywordsArticleSet.addAll(containingKeywordArticleList);
        }

        // 점수들을 계산합니다.
        List<ArticleAndScorePair> articleAndScorePairList = new ArrayList<>(containingAnyKeywordsArticleSet.size());
        containingAnyKeywordsArticleSet.forEach(article -> {
            Integer score = 0;

            for (String keyword : keywordsSplitted) {
                score += StringUtils.countOccurrencesOf(article.getTitle(), keyword) * TITLE_CONTAINING_KEYWORDS_SCORE;
                score += StringUtils.countOccurrencesOf(article.getContents(), keyword) * CONTENTS_CONTAINING_KEYWORDS_SCORE;
            }

            articleAndScorePairList.add(new ArticleAndScorePair(article, score));
        });

        // Article들을 점수로 정렬합니다.
        Collections.sort(articleAndScorePairList);

        // 정렬된 Article을 반환합니다.
        List<ArticleSummaryResponseDto> sortedArticleList = new ArrayList<>(containingAnyKeywordsArticleSet.size());
        for (ArticleAndScorePair pair : articleAndScorePairList) {
            sortedArticleList.add(new ArticleSummaryResponseDto(pair.getArticle()));
        }

        return sortedArticleList;
    }

}
