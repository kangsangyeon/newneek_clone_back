# 뉴닉 클론코딩 Backend

![](https://newneek.co/static/media/logo.334be7e9.png)

[![Preview](https://img.youtube.com/vi/Brz8WhktKeY/0.jpg)](https://www.youtube.com/watch?v=Brz8WhktKeY)

원본 사이트 : [링크](https://newneek.co/)

프론트 Repository: [프론트 Github](https://github.com/chochoq/newneek_clone_front)

<br>

# Background

항해99 커리큘럼 중 클론코딩 주차에서 만난 조원들끼리 1주일간 클론할 수 있는 서비스를 모색하고, <br>

그 중 적당히 도전할만한 과제가 포함되어 있다고 판단한 뉴닉이라는 웹서비스를 클론하게 되었습니다.

<br>

# 프로젝트  개요

## 기술 스택

- FrontEnd: Javascript, React
- BackEnd: Spring Boot, MySQL, JPA, Selenium (for Scraping)
- AWS: EC2, S3

## 특징

- RESTful API 제공
- JPA를 통해 DB의 테이블과 ORM
- Selenium을 사용하여 서버를 시작할 때 최초 1회에 한해 원본 웹사이트에서 데이터를 스크래핑하여 DB에 저장

<br>

# 주요기능

- 메인 페이지
    - 전체 카테고리의 최신 뉴스를 제공
    
    - 페이지네이션을 적용해 모든 뉴스들이 한 번에 보여지지 않고 "더 보기" 버튼을 누를 때마다 뉴스 목록에 그 다음 카드들이 추가

- 카테고리 페이지
    - 각각의 카테고리에 맞는 최신 뉴스들의 목록을 제공

- 검색 페이지
    - 키워드 기반 검색으로, 검색어가 정확히 제목 또는 내용과 일치하지 않아도 포함 여부를 판단해 검색 결과에 포함되도록 제공
    
    - 검색 키워드가 얼마나 글과 연관성이 있는지 판단하기 위해 제목과 내용에서 키워드가 나오는 빈도수에 따라 가중치를 매기고, 가중치에 따라 뉴스 검색 결과 순위가 결정되도록 구현
