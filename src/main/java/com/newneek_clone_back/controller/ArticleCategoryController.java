package com.newneek_clone_back.controller;

import com.newneek_clone_back.Const;
import com.newneek_clone_back.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = Const.AllowOrigins)
@RequiredArgsConstructor
@Controller
public class ArticleCategoryController {
    private final ArticleCategoryService categoryService;
}
