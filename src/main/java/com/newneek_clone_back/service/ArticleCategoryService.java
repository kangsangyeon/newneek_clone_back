package com.newneek_clone_back.service;

import com.newneek_clone_back.entity.ArticleCategory;
import com.newneek_clone_back.repository.ArticleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleCategoryService {
    private final ArticleCategoryRepository categoryRepository;

    public ArticleCategory findByName(String name) {
        try {
            return categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException());
        } catch (IllegalArgumentException e) {
            ArticleCategory newCategory = new ArticleCategory();
            newCategory.setName(name);

            return categoryRepository.save(newCategory);
        }

    }
}
