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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing//time알아서 변경
public class NewneekCloneBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewneekCloneBackApplication.class, args);
    }

//    @Bean
    public CommandLineRunner crawlArticles(ArticleService articleService) {
        return (args -> {
            Path path = Paths.get(System.getProperty("user.dir"), ("src/main/resources/chromedriver.exe"));

            // WebDriver 경로 설정
            System.setProperty("webdriver.chrome.driver", path.toString());

            // WebDriver 옵션 설정
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            options.addArguments("--start-maximized");            // 전체화면으로 실행
            options.addArguments("--disable-popup-blocking");    // 팝업 무시
            options.addArguments("--disable-default-apps");     // 기본앱 사용안함
            //주의:팝업창 안뜨게 만들기 잊지말기!!! 중요함...


            // WebDriver 객체 생성
            ChromeDriver driver = new ChromeDriver(options);

            // 빈 탭 생성
            driver.executeScript("window.open('about:blank','_blank');");

            // 탭 목록 가져오기
            List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

            //첫번째 탭으로 전환
            driver.switchTo().window(tabs.get(0));

            //웹페이지 요청
            driver.get("https://newneek.co/");

            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("loadmore")));

            for (int i = 0; i < 15; i++) {
                driver.findElementByClassName("loadmore").sendKeys(Keys.ENTER);

                String waitTargetSelector = String.format(".card:nth-child(%d)", 12 + 12 * (i + 1));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(waitTargetSelector)));
            }

            //각 카드 href로 들어가기
            List<WebElement> webElements = driver.findElementsByXPath("//*[@id=\"root\"]/div/section/div/a");
            List<String> detailsUrlList = new ArrayList<>();
            for (int i = 0; i < webElements.size(); i++) {
                String url = webElements.get(i).getAttribute("href");
                detailsUrlList.add(url);
            }

            //각 url에 들어가서 제목, 카테고리, 내용 가지고 오기
            for (int i = 0; i < detailsUrlList.size(); i++) {

                driver.get(detailsUrlList.get(i));

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/header/h2")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/header/a")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/section/div/div/div[1]")));

                String title = "";
                String categoryName = "";
                String image = "";
                String contents = "";
                LocalDate date;

                //제목가지고 오기
                WebElement postWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/header/h2");
                title = postWebElement.getText();

                //카테고리 가지고 오기
                WebElement cateWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/header/a");
                categoryName = cateWebElement.getText();

                //날짜 가지고 오기
                WebElement dateWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/header/time");
                String dateText = dateWebElement.getText();
                date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

                //이미지 가지고 오기 /이미지가 없을 때를 대비
                try {
                    WebElement imageWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/div/div[1]/img");
                    image = imageWebElement.getAttribute("src");

                } catch (NoSuchElementException e) {
                }

                //내용 가지고 오기
                WebElement contentWebElement = driver.findElementByXPath("//*[@id=\"root\"]/div/section/div/div/div[1]");
                contents = contentWebElement.getAttribute("innerHTML");


                ArticleRequestDto requestDto = new ArticleRequestDto(title, image, contents, categoryName);
                Article newArticle = articleService.create(requestDto);
                articleService.update(newArticle, (article) -> {
                    article.setCrawledCreatedAt(date);
                });
            }

            driver.close();
            driver.quit();

        });
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