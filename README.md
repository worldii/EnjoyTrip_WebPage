# EnjoyTrip 
여행 관련 홈페이지를 제작.(리팩토링 후) 

## 기간
2023.05-2023.05 (2인 프로젝트)

## 간편 설명
여행을 즐기는 사람의 입장에서, 여행관련된 홈페이지를 제작해보고자 만든 웹사이트 입니다. 현재 참여하고 있는 SSAFY 부트 캠프의 조원인 한명과 기능별로 나누어 백엔드/ 프론트 엔드를 동시에 개발했습니다. 

## 상세 설명
`MyBatis` 를 처음 이용하여 dao, service 를 설계 및 구축했다는 것이 의미있었습니다. 또한 `vue.js,`  `vuex` 를 사용하여, 백엔드 단에서 넘어온 데이터를 활용해볼 수 있어 인상깊었습니다. 또한 프론트 엔드를 제작하기 앞서, figma 를 이용하여 프로토 타입을 제작해본 경험도 좋았던 것 같습니다. 회원가입/로그인을 할 때, jwt 토큰 발급 과정을 이해하고, spring 단에서 `interceptor`를 이용, 그리고 `redis` 를 활용해 성능 향상을 시켰습니다. 핫플레이스와 여행 경로 계획에서는 각각 `kakao Map API` , `kakao Mobility API` 를 사용하여 구현하였습니다. 

## 주요 기능
핫플레이스 장소 등록. 핫플레이스에 대한 의견 등록. 핫플레이스 투표 기능. 공지사항, 커뮤니티 기능. 회원가입/ 로그인 기능, 여행 경로, 계획 기능 
## 기술 스택
### BackEnd
Java, Spring Boot,  mybatis
### FrontEnd
vue.js, bootstrap , html, css, javascript
### 기타
redis, amazon s3 ,mysql

### figma 프로토타입 제작
[https://www.figma.com/file/QXMWevtcOnNwYgn5jC985j/Web-and-mobile-login-screen-(Community)?type=design&node-id=205%3A184&t=qZbtQVDEqXQFQ4YO-1](https://www.figma.com/file/QXMWevtcOnNwYgn5jC985j/Web-and-mobile-login-screen-(Community)?type=design&node-id=205%3A184&t=qZbtQVDEqXQFQ4YO-1)

## 기록
### Notion
https://infrequent-quit-9d3.notion.site/ENJOYTRIP-db521fac87f54caeaaff4e94913e4e7a

# 첨부 자료 
### Home
<img width="500" alt="home" src="https://github.com/worldii/SSAFY_FINAL/assets/87687210/364ce327-53a2-49d3-b1fd-56fbcfffd655"><br>
### Login 및 회원 가입
<img width="500" alt="login" src="https://github.com/worldii/SSAFY_FINAL/assets/87687210/75ae1f64-9f38-462a-9852-78a7704d37e2"><br>
<img width="500" alt="signup" src="https://github.com/worldii/SSAFY_FINAL/assets/87687210/e607b6f5-bc55-4921-84ca-04167062534d"><br>
### hotplace 등록
<img width="500" alt="hotplace" src="https://github.com/worldii/SSAFY_FINAL/assets/87687210/0665c2fc-df33-47de-9a09-927350c38e4f"><br>
<img width="500" alt="plan" src="https://github.com/worldii/SSAFY_FINAL/assets/87687210/a8d6ab2f-db7a-4fe3-9af3-bc7f9e4f5d19">
### 여행 계획 짜기
<img width="500" alt="plan" src="https://github.com/worldii/SSAFY_FINAL/assets/87687210/170cc67f-cd98-4329-b7b7-c178baf6e549"><br>
