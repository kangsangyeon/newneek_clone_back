package com.newneek_clone_back.controller;

import com.newneek_clone_back.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;
}
