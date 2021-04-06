package com.newneek_clone_back;

import com.newneek_clone_back.dto.ArticleRequestDto;
import com.newneek_clone_back.entity.Article;
import com.newneek_clone_back.service.ArticleService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJpaAuditing//time알아서 변경
public class NewneekCloneBackApplication {
    public static void main(String[] args) {

        SpringApplication.run(NewneekCloneBackApplication.class, args);

        Path path = Paths.get(System.getProperty("user.dir"), ("src/main/resources/chromedriver.exe"));

        // WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", path.toString());

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
//        options.addArguments("--start-maximized");            // 전체화면으로 실행
//        options.addArguments("--disable-popup-blocking");    // 팝업 무시
//        options.addArguments("--disable-default-apps");     // 기본앱 사용안함
        //주의:팝업창 안뜨게 만들기 잊지말기!!! 중요함...


        // WebDriver 객체 생성
        ChromeDriver driver = new ChromeDriver(options);
//
//        // 빈 탭 생성
        driver.executeScript("window.open('about:blank','_blank');");

        // 탭 목록 가져오기
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        //첫번째 탭으로 전환
        driver.switchTo().window(tabs.get(0));

        //웹페이지 요청
        driver.get("https://newneek.co/");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        for (int i = 1; i < 16; i++) {

            driver.findElementByClassName("loadmore").sendKeys(Keys.ENTER);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
        //셀레니움 **

        //전체 로드 페이지 조회
        System.out.println(driver.getPageSource());
        //각 카드 href로 들어가기
        List<WebElement> webElements = driver.findElementsByXPath("//*[@id=\"root\"]/div/section/div/a");
        List<String> detailLinkList = new ArrayList<>();
        for (int i = 0; i < webElements.size(); i++) {
            String url = webElements.get(i).getAttribute("href");
            detailLinkList.add(url);
            System.out.println(url);
        }
        //각 url에 들어가서 제목, 카테고리, 내용 가지고 오기

        for (int i = 0; i < detailLinkList.size(); i++) {

            driver.get(detailLinkList.get(i));

            WebDriverWait wait = new WebDriverWait(driver, 20);
            //동기 함수
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/header/h2")));


            //제목가지고 오기
            WebElement postWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/header/h2");
            System.out.println(postWebElement.getText());
            String title = postWebElement.getText();

            //카테고리 가지고 오기
            WebElement cateWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/header/a");
            System.out.println(cateWebElement.getText());
            String categoryName = cateWebElement.getText();

            //이미지 가지고 오기 /이미지가 없을 때를 대비
            try {
                WebElement imageWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/div/div[1]/img");
                System.out.println(imageWebElement.getAttribute("src"));
                String image = imageWebElement.getAttribute("src");

            } catch (NoSuchElementException e) {
            }
            WebDriverWait wait1 = new WebDriverWait(driver, 20);
            //동기 함수
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/div/div[1]")));

            //내용 가지고 오기
            WebElement contentWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/div/div[1]");
            System.out.println(contentWebElement.getAttribute("innerHTML"));
            String contents = contentWebElement.getAttribute("innerHTML");





//            ArticleRequestDto requestDto = new ArticleRequestDto(title, image, contents, categoryName);
//
//            articleService.create(requestDto);
        }
        driver.close();
        driver.quit();
//        ArticleRequestDto requestDto = new ArticleRequestDto(title, imageUrl, contents, categoryName);
//
//        articleService.create(requestDto);

    }


//    @Bean
//    public CommandLineRunner generateDummyArticles(ArticleService articleService) {
//        return (args) -> {
//            String[] ARTICLE_CATEGORIE_NAMES = new String[]{"5분뉴닉", "국내정치", "국제·외교", "경제", "노동·일", "인권", "테크", "문화", "환경·에너지", "코로나19"};
//            int DUMMY_ARTICLES_COUNT = 30;
//
//            Random rand = new Random();
//
//            for (int i = 0; i < DUMMY_ARTICLES_COUNT; i++) {
//                int randomNumber = rand.nextInt(10000);
//                String title = "제목 " + randomNumber;
//                String contents = "내용 " + randomNumber;
//                String imageUrl = randomNumber % 2 == 0 ? "" : "이미지url " + randomNumber;
//                String categoryName = ARTICLE_CATEGORIE_NAMES[randomNumber % ARTICLE_CATEGORIE_NAMES.length];
//
//                ArticleRequestDto requestDto = new ArticleRequestDto(title, imageUrl, contents, categoryName);
//
//                articleService.create(requestDto);
//
//
//
//            }
//        };
//    }

}