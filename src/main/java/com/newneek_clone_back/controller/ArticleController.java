package com.newneek_clone_back.controller;

import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @ResponseBody
    @GetMapping("/api/articles")
    public String getArticles() {
        JSONObject jsonObject = new JSONObject();

        Article article = new Article("제목", "이미지url", "내용내용");
        Article article1 = new Article("제목111", "이미지url1", "내용내용1");
        Article article2 = new Article("제목222", "이미지url2", "내용내용2");
        Article article3 = new Article("제목333", "이미지url3", "내용내용3");

        ArrayList<Article> articleList = new ArrayList<Article>();
        articleList.add(article);
        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);

        jsonObject.put("articleList", articleList);

        return jsonObject.toString();
    }

}
