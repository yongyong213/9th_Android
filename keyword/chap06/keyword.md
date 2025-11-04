- Lifecycle
  - Lifecycle이란 무엇일까요?
    - 안드로이드의 Activity나 Fragment 같은 컴포넌트가 생성되어서 사라지기까지 거치는 상태 변화 과정이다.
  - Lifecycle은 왜 등장하게 되었을까요?
    - 복잡한 리소스 관리와 메모리 누수 문제를 해결하기 위해 등장
    - 만약 Activity나 Fragment 안에서 mediaPlayer나 Timer와 같은 쓰레드를 사용하는 상황에서 생명주기가 없다면 Activity나 Fragment가 종료되어도 백그라운드에서 mediaPlayer나 Timer 등이 계속 실행되어 메모리나 배터리 소모가 크다.
- Activity의 Lifecycle
  - Activity의 대표적인 Lifecycle은 어떤게 있을까요?
    ![image.png](attachment:68dc4112-5633-42a2-92fd-dad1203915b9:image.png)
    1. onCreate()
       - 최초에 단 한 번만 수행
       - 화면 Layout, View 생성, Databinding 구현
       - RecyclerView, 어댑터 초기화
    2. onStart()
       - Activity가 화면에 표시되기 직전에 호출
         - onCreate()나 onRestart() 다음
       - 화면에 다시 나타날 때마다 사
    3. onResume()
       - Activity가 화면에 보여지는 직후에 호출
    4. onPause()
       - Activity가 사용자에게 포커스아웃 되어 있는 상태
       - 화면에 보여지지 않게 된 직후에 호출
         - 다른 팝업이 뜰 때
       - 무거운 작업을 수행하진 않도록 해야 함
         - 자동 저장, 음악/동영상 일시정지, gps 위치 수신 중단
    5. onStop()
       - Activity가 화면에서 완전히 사라졌을 때(홈 키 눌렀을 때)
       - 무거운 리소스를 정리하고 백그라운드 작업 시작
    6. onDestroy()
       - Activity가 완전히 종료되기 직전에 호출
       - 핵심 리소스 완전 해제
       - 쓰레드 종료
    7. onRestart()
       - onStop() 이후에 다시 기존 Activity로 돌아오는 경우
       - onRestart() 호출 이후 이어서 onStart()가 호출
         - `onStop()` -> `onRestart()` -> `onStart()`
  - 각 Lifecycle을 활용하는 실제 예시들은 어떤게 있을까요?
    - `onCreate()`
      - 앱이 처음 켜질 때 1번만
    - `onResume()`
      - SeekBar를 움직이는 쓰레드 시작, 음악 재생 시작
    - `onPause()`
      - 홈 버튼 누르기 시작할 때 음악 일시 정지, SeekBar 쓰레드 멈춤
    - `onStop()`
      - 홈 화면으로 나갔을 때 화면에서 쓰던 리소스 정리
    - `onRestart()`
      - 다시 원래 노래 화면으로 돌아왔을 때
      - onStop()에서 중지시켰던 작업 다시 시작할 준비
    - `onStart()`
      - 화면이 사용자에게 보이기 시작할 때
      - onStop()에서 중지시켰던 작업 다시 시작
      - 화면에 표시되는 데이터 갱신
    - `onDestory()`
      - 뒤로가기를 눌러 앱이 완전히 종료될 때
      - 쓰레드 완전 종료, MediaPlayer 리소스 해제
- SharedPreferences
  - SharedPreference란 무엇일까요?
    - key-value 형태로 간단한 데이터를 저장
    - 자동 로그인 여부, 알림 수신 동의 여부 등이 저장된다.
  - SharedPreference는 어떤 방식으로 값을 저장할까요?
    - 앱의 전용 저장 공간에 데이터를 XML 파일로 저장한다.
    - SharedPreferences.Editor 객체를 사용하여 저장한다.
    - 데이터 파일이 앱 내부에 저장되기 때문에 앱을 삭제하면 같이 지워진다.
  - JSON과 GSON이란 무엇일까요?
    - JSON
      - key-value 쌍으로 이루어져 있으며 xml보다 가볍고 가독성이 좋다
        ```cpp
        {
        	"name": "용승",
        	"id": 12223716
        }
        ```
    - GSON
      - google에서 만든 java/kotlin 라이브러리
      - JSON 문자열과 자바/코틀린의 객체를 서로 변환해주는 역할을 한다.
      - 직렬화: 객체 → 문자열
      - 역직렬화: 문자열 → 객체
      - - SharedPreferences 는 객체를 저장할 수 없기 때문에 HSON을 통해 객체를 직렬화하여 저장한다.
