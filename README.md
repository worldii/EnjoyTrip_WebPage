# EnjoyTrip 
여행 관련 홈페이지를 제작.(리팩토링 후) 

리팩토링 기록
https://jonghadailywrite.notion.site/EnjoyTrip-Refactor-Now-3c8d730a17db442e832999eb860d64ea?pvs=4
## 기간
2023.05-2023.05 (2인 프로젝트)

## 간편 설명
여행을 즐기는 사람의 입장에서, 여행관련된 홈페이지를 제작해보고자 만든 웹사이트 입니다. 현재 참여하고 있는 SSAFY 부트 캠프의 조원인 한명과 기능별로 나누어 백엔드/ 프론트 엔드를 동시에 개발했습니다. 

## 상세 설명
`MyBatis` 를 처음 이용하여 dao, service 를 설계 및 구축했다는 것이 의미있었습니다. 또한 `vue.js,`  `vuex` 를 사용하여, 백엔드 단에서 넘어온 데이터를 활용해볼 수 있어 인상깊었습니다. 또한 프론트 엔드를 제작하기 앞서, figma 를 이용하여 프로토 타입을 제작해본 경험도 좋았던 것 같습니다. 회원가입/로그인을 할 때, jwt 토큰 발급 과정을 이해하고, spring 단에서 `interceptor`를 이용, 그리고 `redis` 를 활용해 성능 향상을 시켰습니다. 핫플레이스와 여행 경로 계획에서는 각각 `kakao Map API` , `kakao Mobility API` 를 사용하여 구현하였습니다. 

## 기타 사항 
총 세번에 나누어 걸쳐 리팩토링을 진행하였습니다.
1. servlet, jsp 사용 https://github.com/worldii/EnjoyTrip_servlet
2. spring boot, jsp 사용 https://github.com/worldii/EnjoyTrip_WebPage_ver1
3. spring boot, vue (현재) 
## 주요 기능
핫플레이스 장소 등록. 핫플레이스에 대한 의견 등록. 핫플레이스 투표 기능. 공지사항, 커뮤니티 기능. 회원가입/ 로그인 기능, 여행 경로, 계획 기능 
## 기술 스택
### BackEnd
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=#007396" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/MyBatis-02303A?style=for-the-badge&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Redis-000000?style=for-the-badge&logo=Redis&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black">

### FrontEnd
 <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"><img src="https://img.shields.io/badge/html-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/axios-007CE2?style=for-the-badge&logo=axios&logoColor=white" >
  <img src="https://img.shields.io/badge/Kakao Map Api-007CE2?style=for-the-badge&logo=KaKao Map Api&logoColor=white">
  <img src="https://img.shields.io/badge/bootstrap-1572B6?style=for-the-badge&logo=bootstrap&logoColor=white">
  <img src="https://img.shields.io/badge/vuejs-E34F26?style=for-the-badge&logo=vuejs&logoColor=white">

### 기타
  <img src="https://img.shields.io/badge/vim-%23121011.svg?style=for-the-badge&logo=vim&logoColor=white"><img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=NGINX&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>

### 협업툴
<img src="https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white"><img src="https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/GitLab-FC6D26?style=for-the-badge&logo=GitLab&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/> <img src="https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=Mattermost&logoColor=white" style="height : auto; margin-left : 10px; margin-right : 10px;"/>
  <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white"/>

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
