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


- 구성 내용
  * Todo 관리를 위해 todo table을 생성하였습니다.
  * 참조관계 TABLE은 todo, todo_refer를 사용하여 Todo 간 참조관계를 설정하였습니다.
  * todo_refer table 은 복합키를 설정하여 자기자신을 참조하지 못하도록 설정하였습니다.
  * CustomExceptionHandler를 사용하여 Exception 구조를 통일하였습니다.
  * REST API 표준을 지켜 API 구성
    * METHOD로 역활 구분
    * 명사위주의 URL
    * 복수형 

- TABLE 
  * todo
    * column list : Long id, String title, Boolean isfinish, TIMESTAMP created_date, TIMESTAMPE modified_date
    * PK : id
  * todo_refer
    * column list : Long id, Long refer_id
    * PK : id, refer_id
    
- API LIST
  * GET /todos/ : Todo List 목록 제공(Paging 방식)
  * GET /todos/select : Todo 등록 시 참조용 Todo List 제공, 미완료 목록만 제공
  * GET /todos/{id}/select : Todo 수정 시 참조용 Todo List  제공, 자기 자신 제외, 미완료 목록만 제공, 자신을 참조하고 있는 Todo 제외
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
