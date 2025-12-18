- RESTful API
    - REST(Representational State Transfer)란 무엇일까요?
        - 자원을 이름(URI)로 구분하여, 해당 자원의 상태를 주고받는 모든 것을 의미한다.
    - REST API의 구성 요소에는 무엇이 있고, 각각 무슨 역할을 할까요?
        1. 자원 (Resource) : HTTP URI(자원의 고유 ID)
        2. 자원에 대한 행위 (Verb) : HTTP Method (GET, POST, PUT, DELETE 등)
        3. 자원에 대한 행위의 내용 (Representation) : HTTP Message Pay Load
    - RESTful API란 무엇일까요?
        - REST의 설계 원칙을 잘 지켜서 설계된 API를 말한다
            - Server-Client (서버 클라이언트 구조)
            - Stateless (무상태)
            - Cacheable ( 캐시 처리 가능)
            - Layered System(계층화)
            - Uniform Interace(인터페이스 일관성)
    - RESTful API에서 사용하는 중요한 개념
        - HTTP Method는 무엇일까요?
            - 클라이언트가 서버에게 자원에 대해 어떤 행동을 하길 원하는지 알려주는 수단(조회, 생성, 수정, 삭제)
        - HTTP Status Code는 무엇일까요?
            - 클라이언트의 요청이 성공했는지, 실패했는지 등 서버 처리 결과를 알려주는 숫자 코드
        - HTTP Status Code의 종류에는 무엇이 있을까요?
            
            
            | **Code** | **언제 나타날까요??** |
            | --- | --- |
            | **1xx** | 정보 : 요청을 받았고, 프로세스를 계속 진행한다 |
            | **2xx** | 성공 : 요청을 성공적으로 받음 |
            | **3xx** | 리다이렉션 : 요청을 완료하기 위해 추가 작업 필요할 때 |
            | **4xx** | 클라이언트 오류 : 요청을 처리하지 못할 때 |
            | **5xx** | 서버 오류 : 서버가 요청을 수행하지 못할 때 |
- Paging
    - Paging이란 무엇일까요?
        - 대량의 데이터를 효율적으로 처리하고 사용자에게 필요한 양만큼씩 나누어 제공하는 기
    - Paging의 예시에는 무엇이 있을까요?
        - 인스타 돋보기처럼 무한 스크롤
        - 댓글 하단의 ‘더보기’
        - 게시물 페이지 [1] [2] [3] [4]…
    - Paging이 필요한 이유는 무엇일까요?
        - 앱 성능 최적화: 많은 데이터를 한 번에 불러오면 로딩이 매우 느려지거나, 메모리 부족이 생겨 앱이 종료됨
        - 네트워크 데이터 절약 : 사용자가 보지 않을 데이터까지 미리 불러올 필요 없음
        - 로딩 속도 향상
- 디자인 패턴
    - 대표적인 디자인 패턴에는 무엇이 있고 어떤 방식으로 작동하나요?
        1. MVC
            
            ![image.png](attachment:eed6fa6f-2c01-45bb-a640-9ea9cca8a8dc:image.png)
            
            - Model + View + Controller
            - 사용자는 Controller에 입력을 주면 Controller가 Model을 업데이트한다.
            - Controller는 Model을 나타내줄 View를 선택하고, View는 Model을 이용해 화면을 나타낸다.
            - Controller : View는 일대다 구조이다
            - 가장 단순하지만 코드가 커지고, View와 Model 사이의 의존성이 높아 유지보수가 어려워진다.
        2. MVP
            
            ![image.png](attachment:b4f4197a-7918-494f-b78f-a6496a210084:image.png)
            
            - Model + View + Presenter
            - Model과 View는 MVC와 동일하다.
            - 사용자의 입력이 View를 통해 들어온다.
            - Presenter는 View에게 데이터를 요청하고 Model에게 데이터를 요청하고 가공한뒤 View가 해당 데이터를 다시 받아 화면을 나타낸다.
            - View와 Model을 서로 알지 못한다. (의존성 없음)
            - Presentr와 View 는 일대일 관계이다.
            - 대신 View와 Presenter 사이에 강한 의존성이 생긴다.
        3. MVVM
            
            ![image.png](attachment:3c02f246-b9b8-41aa-b379-0cbcd79e973c:image.png)
            
            - 구글이 권장하는 최신 아키텍처
            - Model + View + View Model
            - View Model은 View를 표현하기 위해 만든 View를 위한 Model로, View를 나타내주기 위한 Model이자, View를 나타내기 위한 데이터 처리를 하는 부분이다.
            - 사용자 입력을 View를 통해 받으면, Command 패턴으로 View Model에 전달된다.
            - View Model은 Model에게 데이터를 요청하고, Model은 View Model에게 요청받은 데이터를 응답한다.
            - View Model은 응답 받은 데이터를 가공하여 저장하고, View는 View Model과 Data Binding하여 화면을 나타낸다.
            - View Model 과 View는 일대다 관계
            - View 와 Model 사이의 의존성이 없고, Command 패턴과 Data Binding을 사용하여 View 와 View Model 사의의 의존성도 없다
            - 대신 View Model 설계가 어렵다