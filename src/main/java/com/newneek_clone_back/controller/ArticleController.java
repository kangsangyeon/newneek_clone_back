package com.newneek_clone_back.controller;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @ResponseBody
    @GetMapping("/api/articles")
    public String getArticles() {
        JSONObject jsonObject = new JSONObject();

        return jsonObject.toString();
    }

    @ResponseBody
    @PostMapping("/api/articles")
    public Article createArticle(@RequestBody ArticleRequestDto requestDto) {
        return articleService.create(requestDto);
    }

}
