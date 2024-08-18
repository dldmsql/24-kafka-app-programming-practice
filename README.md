# 프로젝트

## 1. 프로젝트 목표

아파치 카프카 프로그래밍 with 자바 책을 읽으며 학습한 내용 기반으로 실습 프로젝트를 진행한다.

## 2. 프로젝트 구성

### 2.1. 프로젝트 환경

- Java 17
- Spring Boot 3.2.2
- Gradle

### 2.2 프로젝트 구조

- producer (서버)
- consumer (서버)
- kafka (모듈)

## 3. 프로젝트 실행

git pull 받기

docker-compose up -d

producer 서버 실행

consumer 서버 실행

프로듀서에서 아래 curl 실행

```shell
curl --location 'localhost:8080/api/email' \
--header 'Content-Type: application/json' \
--data-raw '{
    "to" : "test@gmail.com",
    "subject" :"test",
    "text" : "hi 2"
}'
```

실제 입력한 이메일로 메일이 발송되는지 확인한다.


Ref. https://github.com/ryoobib/backend_webflux 