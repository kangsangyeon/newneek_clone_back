package com.newneek_clone_back.service;

import com.newneek_clone_back.repository.ArticleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleCategoryService {
    private final ArticleCategoryRepository categoryRepository;
}
