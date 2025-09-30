- Activity
  - Activity란 무엇인가요?
    - 안드로이드 앱의 화면을 담당하는 기본 구성 요소
    - ex)로그인 화면, 메인 화면, 설정 화면 등
- Activity-Layout 결합
  - findViewById
    - findViewById 사용법
      ```kotlin
      val happyText: TextView = findViewById(R.id.text_happy)
      happyText.setOnClickListener{
      	//클릭 시 해당하는 내용
      }
      ```
  - ViewBinding
    - ViewBinding 사용법
      ```kotlin
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.GoBackButton.setOnClickListener{
      		finish()
      }
      ```
  - findViewById보다 ViewBinding이 권장되는 이유
    - findViewById의 단점
      - 뷰를 찾지 못하면 null을 반환하여 null 안정성이 떨어진다
      - 찾은 뷰의 타입을 직접 캐스팅해야한다
      - 뷰가 많아질수록 성능이 저하된다
    - ViewBinding의 장점
      - 존재하지 않는 뷰에 접근하면 컴파일 오류가 되어 null 안정성이 높다
      - 뷰의 타입을 알아서 캐스팅해준다
      - 성능이 findViewById에 비하여 훨씬 빠르다
- 새 Activity를 띄우는 방법
  - Intent를 이용해 새 액티비티를 띄우는 방법은 무엇인가요?
  ```kotlin
  //Intent를 생성하고 startActivity()호출
  Intent intent = new Intent(this, Activity.class);

  startActivity(intent);
  ```
  - Activity간 데이터를 전달하려면 어떻게 해야 하나요?
    1. 데이터 보내기

       ```kotlin
       val intent = Intent(this, SecondActivity::class.java)

       // 키-값 형태로 데이터 추가
       intent.putExtra("user_name", "홍길동")
       intent.putExtra("user_age", 25)

       // intent를 담아 액티비티 시
       startActivity(intent)
       ```

    2. 데이터 받기

       ```kotlin
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_target);

           // 인텐트를 가져오기
           Intent intent = getIntent();

           // `get...Extra()` 메서드를 사용하여 데이터 추출
           val name = intent.getStringExtra("user_name")
       		val age = intent.getIntExtra("user_age", 0) // 기본값 0

           // 받아온 데이터를 활용합니다.
           TextView textView = "이름: $name, 나이: $age"
       }
       ```
- Fragment
  - Fragment란 무엇인가요?
    - Activity 내에서 재사용 가능한 UI 영역으로 activity에 포함되어야만 존재할 수 있다
    - 자체적인 수명 주기를 가진다
  - Fragment를 사용하는 이유는 무엇인가요?
    1. 재사용성: 하나의 fragment를 여러 activity에 사용하거나 한 activity에 여러 fragment를 사용할 수 있다.
    2. 모듈화: 복잡한 UI를 여러개의 fragment로 나누어 관리하여 유지 보수가 쉽고, 코드의 가독성 또한 높아진다.
    3. 다양한 화면 크기 지
  - Fragment는 어떻게 하면 화면에 보이게 할 수 있나요?
    1. 레이아웃 파일에 선언: 가장 간단한 방법, xml 레이아웃 파일에 `<fragment>` 태그를 직접 추가한다.

       ```kotlin
       <fragment
           android:id="@+id/my_fragment"
           android:name="com.example.myapp.MyFragment"
           android:layout_width="match_parent"
           android:layout_height="match_parent" />
       ```

    2. 동적으로 추가: FragmentManger를 사용하여 런타임에 fragment를 액티비티에 동적으로 추가, 교체, 제거 한다.

       - **`FragmentManager`** 얻기: `getSupportFragmentManager()` 메서드를 사용
       - **`FragmentTransaction`** 시작: `beginTransaction()` 메서드를 호출
       - **`add()` 또는 `replace()`**: 프래그먼트를 화면에 추가하거나 기존 프래그먼트를 교체
       - **`commit()`**: 트랜잭션을 실행하여 변경 사항을 적용

       ```kotlin
       FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

       // 화면의 'fragment_container'라는 ID를 가진 레이아웃에 MyFragment를 추가
       fragmentTransaction.add(R.id.fragment_container, new MyFragment());

       // 트랜잭션 실행
       fragmentTransaction.commit();
       ```
- FragmentManager
  - FragmentManager란 무엇인가요?
    - 액티비티 내에서 Fragment를 관리하는 클래스
  - FragmentManager는 언제 사용해야 될까요?
    - 프래그먼트를 **동적으로 제어**해야 할 때 사용한다.
    1. 프래그먼트 추가, 제거, 교체
    2. 프래그먼트 백 스택 관리: 돌아가기 버튼을 눌렀을 때 이전 프래그먼트 상태로 감
    3. 액티비티-프래그먼트 통신: 데이터를 주고 받거나 메서도를 호출할 때, `findFragmentById()` 같은 메서드로 프래그먼트의 인스턴스를 가져온다
    4. 다양한 화면 크기 지원: 태블릿에 여러 프래그먼트를 보여주거나, 좁은 화면에서 프래그먼트를 교체하여 보여주는 **반응형 UI**를 구현할 때 필수적이다
- Bundle
  - Bundle은 무엇일까요?
    - 다양한 종류의 데이터를 Key-Value 쌍으로 저장하는 객체
    - ‘가방’ 같은 역할로 문자열, 정수, 배열, Parcelable(serializable 같은거) 까지도 담을 수 있다.
  - Intent와 Bundle을 결합하는 방법은 무엇일까요?
    - Intent에서 데이터를 보낼 때 사용하는 `putExtra()` 는 데이터를 Bundle에 담아 전달
    - 데이터 보내기: 데이터를 그냥 번들에 다 넣어서 보
    ```kotlin
    Bundle bundle = new Bundle();

    // 2. put...() 메서드를 사용해 다양한 데이터를 bundle에 담기
    bundle.putString("user_name", "홍길동");
    bundle.putInt("age", 25);
    bundle.putStringArray("hobbies", new String[]{"코딩", "운동"});

    // 3. Intent 객체 생성
    Intent intent = new Intent(this, TargetActivity.class);

    // 4. Intent의 putExtras() 메서드를 사용해 bundle을 통째로 추가
    intent.putExtras(bundle);

    // 5. Intent를 담아 액티비티 시작
    startActivity(intent);
    ```
    - 데이터 받기
    ```kotlin
    // 1. Intent에서 Bundle 객체 가져오기
    Bundle bundle = getIntent().getExtras();

    // 2. bundle이 null이 아닌지 확인
    if (bundle != null) {
        // 3. get...() 메서드를 사용해 bundle에서 데이터 추출
        String userName = bundle.getString("user_name");
        int age = bundle.getInt("age");
        String[] hobbies = bundle.getStringArray("hobbies");

        // 추출한 데이터를 활용 (예: TextView에 표시)
        TextView textView = findViewById(R.id.textView);
        textView.setText("이름: " + userName + ", 나이: " + age + ", 취미: " + Arrays.toString(hobbies));
    }
    ```
- BottomNavigationView란?
  - BottomNavigationView란 무엇이고, 어떤 기능을 하나요?
    - 안드로이드 앱 화면 하단에 위치하는 내비게이션 바를 구현하는 UI 컴포넌트
    - 화면 전환, 직관적 적인 탐색, 일관성 있는 UI를 만들 수 있도록 돕는다
    - 화면 전환 시에도 내비게이션 바가 항상 고정되어 있음
- BottomNavigationView의 구성 요소
  - BottomNavigationView를 사용 시 추가 레이아웃을 정의해야 합니다. 이때 정의해야 할 요소들은 무엇인가요?
    - BottomNavigationView 위젯: 레이아웃 파일에 추가되는 실제 UI 컴포넌트
    - Menu 리소스 XML 파일: 네비게이션 바에 표시될 탭들의 목록 정의
    - 프래그먼트: 각 탭을 눌렀을 때 화면에 표시될 프래그먼트 정의
  - Menu 리소스 XML: `res/menu` 디렉토리에 저장
    - <menu> 태그는 무엇일까요?
        - menu 리소스의 루트 요소, 모든 item을 감싸는 컨테이너 역할
    - <item> 태그는 무엇일까요?
      - 네비게이션 바의 개별 탭
    - <item> 태그에 설정할 수 있는 항목에는 무엇이 있을까요?
      - android:id
      - android:title: 탭 아래 표시하는 텍스트 ex) 홈, 검색
      - android:icon
      - android:checkable: 선택 상태를 유지할 수 있는지 기본이 true
      - android:enabled: 탭을 클릭할 수 있는지
      - android:visible: 탭을 화면에 보이게 할지
  - 화면 구성
    ![Untitled](./Untitled.png)
    1. 왼쪽의 예시 화면에서 (A)와 (B)에 해당하는 각각의 알맞은 **화면 구성 요소**를 선택해주세요!
       - (A) = Activity
       - (B) = Fragment
    2. 왼쪽 화면을 구성하기 위해서 일반적으로 몇 개의 Fragment가 필요한가요??
       - 정답 ) 4 개?
       - (B) + 네비게이션 바의 탭에 해당하는 3개의 Fragment
- 안드로이드 XML Naming Convention은 어떤 형식이 있을까요?
  - &lt;WHAT&gt;_&lt;WHERE&gt;_&lt;DESCRIPTION&gt;\_&lt;SIZE&gt; 순서로 작성
    - LAYOUT: &lt;WHAT&gt;\_&lt;WHERE&gt;.xml
      - ex) activity_main.xml
    - STRINGS: &lt;WHERE&gt;\_&lt;DESCRIPTION&gt;
      - ex) main_title
    - DRAWABLES: &lt;WHERE&gt;_&lt;DESCRIPTION&gt;_&lt;SIZE&gt;
      - ex) main_background_small
    - IDS: &lt;WHAT&gt;_&lt;WHERE&gt;_&lt;DESCRIPTION&gt;
      - ex) linearlayout_main_fragmentcontainer
    - DIMENSIONS: &lt;WHAT&gt;_&lt;WHERE&gt;_&lt;DESCRIPTION&gt;\_&lt;SIZE&gt;
      - ex) keyline_all_text
