package com.newneek_clone_back.controller;

import com.newneek_clone_back.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ArticleCategoryController {
    private final ArticleCategoryService categoryService;
}
