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



## 모델링
### 인증 메일 
- `인증 메일`을 회원이 입력한 이메일로 보낼 수 있다.
- 이메일 형식을 검증한다.
- 데이터베이스에 해당 유저가 중복되는지 검사한다.
- '인증 시간 만료 5분'으로 지정한다.  



### 회원 

| 한글명 | 영문명 | 설명             |
| --- | --- |----------------|
| 회원 | member | 사이트 회원         |
| 닉네임 | nickname | 회원 가입한 유저 닉네임  |
| 이메일 | useremail | 회원 가입한 유저 이메일  |
| 비밀번호 | password | 회원 가입한 유저 비밀번호 |
| 인증번호 | userNumber | 이메일 인증번호    |
| 메일 | mail | 유저한테 보내는 메일 |
| 아이디 | userId | 회원 가입한 유저 아이디 |
| 휴대폰 번호 | phoneNumber | 회원 가입한 유저 휴대폰 번호 |
| 클라이언트 | client | web 브라우저에서 보내는 요청 |
| 이메일 인증 | authMail | 이메일 인증 |

- 비회원은 `회원(member)` 가입할 수 있다.
- `닉네임(nickname)`은 비속어를 지정할 수 없다. 
- `이메일(useremail)`은 필수 작성한다.
- `비밀번호(password)` 영문 특수문자 숫자 형식 조합 8-16글자 아닌 경우 회원 가입할 수 없다.
- `비밀번호(password)` 두 개가 일치해야 회원가입이 가능하다.
- `이메일 인증(authMail)` 실패하는 경우 회원 가입할 수 없다. 
- `휴대폰 번호(phoneNumber)` 입력
- `아이디(userId)` 또는 `이메일(useremail)` 중복일 경우, 회원 가입은 불가능하다.
- `비밀번호(password)`, `아이디(userId)`가 `클라이언트(client)`가 올바른 요청을 한다면 로그인한다.
- `회원(member)`은 `비밀번호(password)`를 찾을 수 있다.
- `회원(member)`은 `아이디(userId)`를 찾을 수 있다.
- `회원(member)`은 로그인할 수 있다.
- `회원(member)`은 탈퇴할 수 있다.
- `회원(member)`은 회원가입시, `비밀번호`가 암호화된다.



### 프로필

| 한글명 | 영문명 | 설명                |
| --- | --- |-------------------|
| 회원 | member | 사이트 회원            |
| 닉네임 | nickname | 회원 가입한 유저 닉네임     |
| 이메일 | useremail | 회원 가입한 유저 이메일     |
| 비밀번호 | password | 회원 가입한 유저 비밀번호    |
| 썸네일 | thumbnail | 회원 가입한 유저 프로필 이미지 |
| 휴대폰 번호 | phoneNumber | 회원 가입한 유저 휴대폰 번호  |
| 인증번호 | userNumber | 이메일 인증번호          |
| 프로필 | profile | 회원 가입한 유저 정보들     |

- `회원(member)`은 `닉네임(nickname)` 중복 아닌 경우,  변경할 수 있다.   
- `회원(member)`은 `이메일(useremail)` `인증번호` 일치 and 중복된 이메일 없는 경우, 변경할 수 있다.  
- `회원(member)`은 `이메일(useremail)` `인증번호` 불일치 and 중복된 이메일 있는 경우, 변경할 수 없다.
- `회원(member)`은 `비밀번호(password)` 영문 특수문자 숫자 형식 조합 8-16글자 인 경우 변경할 수 있다.
- `회원(member)`은 `비밀번호(password)` 영문 특수문자 숫자 형식 조합 8-16글자 아닌 경우 변경할 수 없다.
- `회원(member)`은 `썸네일(thumnail)` 변경할 수 있다.
- `회원(member)`은 `휴대폰 번호(phoneNumber)` (xxx-xxxx-xxxx)형식이 아닌 경우, 변경할 수 없다.
- `회원(member)`은 `휴대폰 번호(phoneNumber)` (xxx-xxxx-xxxx)형식인 경우, 변경할 수 있다.
- `회원(member)`은 `프로필(profile)`조회할 수 있다.
- `회원(member)`은 `썸네일(thumbnail)` 이미지 형식일 경우, 변경할 수 있다.
- `회원(member)`은 `썸네일(thumbnail)` 이미지 형식이 아닌 경우, 변경할 수 없다.