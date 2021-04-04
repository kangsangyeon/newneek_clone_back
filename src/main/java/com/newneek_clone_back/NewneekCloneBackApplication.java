package com.newneek_clone_back;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Random;

@SpringBootApplication
@EnableJpaAuditing//time알아서 변경
public class NewneekCloneBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewneekCloneBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner generateDummyArticles(ArticleService articleService) {
        return (args) -> {
            String[] ARTICLE_CATEGORIE_NAMES = new String[]{"5분뉴닉", "국내정치", "국제·외교", "경제", "노동·일", "인권", "테크", "문화", "환경·에너지", "코로나19"};
            int DUMMY_ARTICLES_COUNT = 30;

            Random rand = new Random();

            for (int i = 0; i < DUMMY_ARTICLES_COUNT; i++) {
                int randomNumber = rand.nextInt(10000);
                String title = "제목 " + randomNumber;
                String contents = "내용 " + randomNumber;
                String imageUrl = randomNumber % 2 == 0 ? "" : "이미지url " + randomNumber;
                String categoryName = ARTICLE_CATEGORIE_NAMES[randomNumber % ARTICLE_CATEGORIE_NAMES.length];

                ArticleRequestDto requestDto = new ArticleRequestDto(title, imageUrl, contents, categoryName);

                articleService.create(requestDto);
            }
        };
    }

}
