Todo-list Application
=============
1. 문제해결 전략
-------------
- 사용 모듈
  * Framework : springboot 2.0.4
  * Language : Java 1.8
  * Bulid Tool : Gradle 4.9
  * DB : H2
  * Front-End : hbs, bootstrap, jquery, datatables
  * Others : JPA, lombok


- API LIST
  * GET  /todos/ : Todo List 목록 제공(Paging 방식)
  * GET  /todos/select      : Todo 등록 시 참조용 Todo List 제공, 미완료 목록만 제공
  * GET  /todos/{id}/select : Todo 수정 시 참조용 Todo List  제공, 자기 자신 제외, 미완료 목록만 제공, 자신을 참조하고 있는 Todo 제외
  * POST /todos/ : Todo 등록
  * PUT /todos/{id} : Todo 수정
  * PUT /todos/{id}/status : Todo 완료 처리
  
2. 빌드 및 실행 방법
-------------
- 명령줄 실행 시프로젝트 경로에서 실행
  * gradle build
  * gradle bootRun
  * 접속 : http://127.0.0.1/8080 

- eclipse
  * Gradle > Refresh Gradle Project
  * Run AS > Spring Boot App
