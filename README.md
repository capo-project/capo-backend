# PhotoCardTradeProject v1.0
- - -

## 프로젝트 소개 
최근 K-POP , E sports, 축구 선수 등 다양한 사람들이 인플루언서 또는 전 세계적으로 활동하며 굿즈를 판매하는 일들 이 많아지고 있습니다.
이러한 거래 사이트가 많지 않고 기존에 존재하는 사이트, 어플은 포토 카드를 판매할 때 불편한 점이 보여 이러한 점들을 개선하고자 개발하게 되었습니다.

## 팀 소개
<div align="center">

|**신효승**| **한승균** | **강은화** |  **여하은**  |
|:-------:|:-------:|:-------:|:---------:|
| 백엔드개발자 | 백엔드개발자 | 프론트개발자  | UI/UX 디자인 |
</div>


## 목차
- - - 

- 요구 사항
- 설치 및 실행 방법 
- 기술 스택 
- 화면 구성/API 주소 
- 주요 기능 
- 아키텍처
- 기타사항


## 실행 방법 

## 기술스택

- Backend: Java, Spring Boot, Spring Data JPA, Spring Security, OAuth2, JWT, Stomp, QueryDSL
- Frontend : React, NextJS
- Database : Mysql
- CI/CD : GitHubActions
- Cloud Platform: AWS
- Email : Google SMTP
- Web Server : nginx
- Langague : JAVA

## 프로젝트 구조
<hr/>

```
capo
├── feature
│   ├── auth
│   ├── file
│   ├── image
│   ├── like
│   ├── logout
│   ├── member
│   ├── message
│   ├── oauth
│   ├── product
│   ├── profile
│   ├── signup
│   ├── temporarily_product
│   └── token
├── global
│   ├── category
│   │   ├── GroupCategory
│   │   └── SocialType
│   ├── code
│   │   ├── ErrorCode
│   │   ├── NickName
│   │   ├── ResultErrorMsgCode
│   │   ├── ResultMsgCode
│   │   └── SuccessCode
│   └── config
│       ├── exception
│       ├── handler
│       └── jwt
├── response
│   ├── ApiResponse
│   └── ErrorResponse
├── utils
├── infra
│   ├── aws
│   ├── firebase
│   └── mail
└── ProjectApplication
```
