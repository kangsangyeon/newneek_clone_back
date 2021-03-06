package com.newneek_clone_back.controller;

import com.newneek_clone_back.Const;
import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.dto.ArticleResponseDto;
import com.newneek_clone_back.dto.ArticleSummaryResponseDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Const.AllowOrigins)
@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @ResponseBody
    @GetMapping(value = "/api/articles", produces = "application/json")
    public String getArticleSummaries(@RequestParam(required = false) String category, @RequestParam(required = false) Integer page) {
        List<ArticleSummaryResponseDto> articleSummaryList = articleService.getArticleSummaryList(category, page);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("category", category);
        jsonObject.put("page", page);
        jsonObject.put("articleSummaryList", articleSummaryList);

        return jsonObject.toString();
    }

    @ResponseBody
    @GetMapping(value = "/api/articles/{id}", produces = "application/json")
    public String getArticleDetails(@PathVariable Long id) {
        try {
            Article article = articleService.findById(id);
            ArticleResponseDto articleResponse = new ArticleResponseDto(article);
            List<ArticleSummaryResponseDto> relativeArticleSummaryList = articleService.getRelativeArticleSummaryList(article);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("article", new JSONObject(articleResponse));
            jsonObject.put("relativeArticleSummaryList", relativeArticleSummaryList);

            return jsonObject.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        return jsonObject.toString();

    }

    @ResponseBody
    @PostMapping(value = "/api/articles", produces = "application/json")
    public Article createArticle(@RequestBody ArticleRequestDto requestDto) {
        return articleService.create(requestDto);
    }

    @ResponseBody
    @GetMapping(value = "/api/search", produces = "application/json")
    public String getArticleUsingKeywords(@RequestParam String keywords) {
        List<ArticleSummaryResponseDto> articleList = articleService.getArticleSummaryListUsingKeywords(keywords);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articleSummaryList", articleList);

        return jsonObject.toString();
    }
}
