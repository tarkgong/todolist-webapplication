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
  * SpringBoot 를 사용하여 프로젝트 설정에 대한 부분을 최소화 하고 로직 구성에 집중하였습니다.
  * bootstrap 과 datatable을 사용하여 ui 구성에 대한 시간을 절약하였습니다.
  * H2 DB를 사용하여 in memory DB로 구성하였습니다.
  * Todo 관리를 위해 todo table을 생성하였습니다.
  * 참조관계 TABLE은 todo, todo_refer를 사용하여 Todo 간 참조관계를 설정하였습니다.
  * BaseEntity를 상속받아 각 Table이 등록/수정시간에 대해서 공통된 로직으로 관리합니다.
  * todo_refer table 은 복합키를 설정하여 자기자신을 참조하지 못하도록 설정하였습니다.
  * CustomExceptionHandler를 사용하여 Valication Exception, RestException 구조로 구성하였습니다.
  * REST API 표준을 지켜 API 구성하였습니다.
    * METHOD로 역활 구분(GET : 조회, POST : 등록, PUT : 수정)
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
    * ex) /todos/?start=0&length=10&columns[]=id&columns[]=title&columns[]=createdDate&columns[]=modifiedDate&columns[]=isfinish&orders[]=0_asc
  * GET /todos/select : Todo 등록 시 참조용 Todo List 제공, 미완료 목록만 제공
    * ex) /todos/select
  * GET /todos/{id}/select : Todo 수정 시 참조용 Todo List  제공, 자기 자신 제외, 미완료 목록만 제공, 자신을 참조하고 있는 Todo 제외
    * ex) /todos/1/select
  * POST /todos/ : Todo 등록
      * ex) /todos/ body : {"title":"화면테스트5","referIds":["3"]}
  * PUT /todos/{id} : Todo 수정
      * ex) /todos/3 body : {"title":"화면테스트2","referIds":["1"]}
  * PUT /todos/{id}/status : Todo 완료 처리
      * ex) /todos/4/status
  
2. 빌드 및 실행 방법
-------------
- 명령줄 실행 시프로젝트 경로에서 실행
  * gradle build
  * gradle bootRun
  * 접속 : http://127.0.0.1/8080 
  * 주의사항 : 한글경로가 포함되 곳에서는 build 되지 않습니다.
  
- eclipse
  * Gradle > Refresh Gradle Project
  * Run AS > Spring Boot App
  * 접속 : http://127.0.0.1/8080 
