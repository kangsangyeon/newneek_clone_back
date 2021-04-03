package com.newneek_clone_back.repository;

import com.newneek_clone_back.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
