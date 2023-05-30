# SSAFY_FINAL
ssafy 관통 프로젝트
여행 관련 홈페이지를 제작. 
## 기술 스택
Spring Boot, mybatis, mysql, vue, bootstrap, redis, amazon s3 

# 기능
- [x] 유저 회원가입, 정보 조회, 로그인
- [x] 게시글, 댓글 CRUD
- [x] 여행 계획 경로 CRUD (카카오 map API, 카카오 모빌리티 API)
- [x] 여행 핫플레이스 등록 CRUD (카카오 map API) 


# 실행 방법
## Frontend
npm run serve
.env 파일 추가하여야 함. 
## backend
s3 파일 업로드 시 secret key 따로 설정 ->  aws configure 해줘야함
