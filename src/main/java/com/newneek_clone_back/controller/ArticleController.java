package com.newneek_clone_back.controller;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final int PAGE_SIZE = 12;

    private final ArticleService articleService;

    @ResponseBody
    @GetMapping(value = "/api/articles", produces = "application/json")
    public String getArticles(@RequestParam(required = false) String category, @RequestParam(required = false) Integer page) {
        List<Article> articleList;

        if (page == null) {
            articleList = category == null ? articleService.findAllByOrderByCreatedAtDesc() : articleService.findAllByCategoryOrderByCreatedAtDesc(category);
        } else {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE);

            Page<Article> articlePage = category == null ? articleService.findAllByOrderByCreatedAtDesc(pageable) : articleService.findAllByCategoryOrderByCreatedAtDesc(category, pageable);
            articleList = articlePage.getContent();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articleList", articleList);
        jsonObject.put("category", category);
        jsonObject.put("page", page);

        return jsonObject.toString();
    }

    @ResponseBody
    @PostMapping(value = "/api/articles", produces = "application/json")
    public Article createArticle(@RequestBody ArticleRequestDto requestDto) {
        return articleService.create(requestDto);
    }

}
