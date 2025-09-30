- TabLayout
  - TabLayout이란 무엇이고, 어떤 기능을 수행하나요?
    - 사용자에게 여러 화면(탭)을 보여주고 그 사이를 쉽게 전환할 수 있도록 하는 가로 형태의 네비게이션 UI 위젯이다. 주로 화면 상단에 표시
    - 기능
      - 화면 분할 및 탐색: 서로 다른 콘텐츠를 가진 여러 프래그먼트를 구분
      - 스와이프 연동: ViewPager2와 함께 사용하면 사용자가 화면을 좌우로 스와이프하며 탭을 전환할 수 있게 한다
      - 시각적 피드백: 현재 어떤 탭이 선택되었는지 시각적으로 표시해줌(탭 이름 색상 변경, 밑줄 표시 등)
  - TabLayout이 사용된 예시에는 무엇이 있나요?
    ![image.png](attachment:34e13a8b-8690-48d6-8bd1-fc20852de7a2:image.png)
- ViewPager2
  - ViewPager란 무엇이고, 어떤 기능을 하나요?
    - 사용자가 화면을 좌우 또는 상하로 스와이프하여 전체 화면을 전환할 수 있도록 해주는 UI라이브러리로 여러 프래그먼트나 뷰를 페이지처럼 넘겨볼 수 있게 한다.
    - 기능
      - 스와이프를 통한 화면 잔환
      - TabLayout과 연동
      - 효율적인 메모리 관리: 화면에 보이지 않는 페이지는 메모리에서 제거하는 방식으로 효율적으로 작동한다
  - ViewPager가 사용된 예시에는 무엇이 있나요?
    - 이미지 갤러리
    - 인스타 스토리
    - 릴스 등
  - ViewPager와 ViewPager2의 차이는 무엇인가요?
    - ViewPager
      - 좌우 스와이프만 지원
      - PagerAdapter기반
      - 데이터 업데이트 시 전체 업데이트
    - ViewPager2
      - 상하 좌우 스와이프 모두 지원
      - RecyclerView.Adepter 기반
      - 아이템 단위의 효율적인 업데이트
- ViewPager2 설정하기
  - ViewPager2를 이용하기 위해 어떤 라이브러리를 사용(implementation)해야 하나요?
    ```kotlin
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    ```
    - `ViewPager2` 위젯과 `FragmentStateAdapter` 같은 클래스들을 포함하고 있다
  - ViewPager2에서 FragmentStateAdapter는 무엇이고 어떤 기능을 하나요?
    - `FragmentStateAdapter`는 `ViewPager2`에게 각 페이지에 어떤 프래그먼트를 보여줘야 할지 알려주는 중간 관리자이다.
    - 기능
      - 스와이프 시에 다음 페이지에 해당하는 프래그먼트 객체 생성하여 ViewPager2에 제공
      - 메모리 효율성: 화면에서 멀어져 보이지 않는 프래그먼트는 메모리에서 제거 but 사용자가 돌아올 수도 있으니 스크롤 위치나 입력값 같은 상태만 저장해둔다
      - 페이지 개수 제공
- ViewPager2 Indicator 설정하기

  - Indicator란 무엇이고, 어떤 역할을 하나요?
    - `Indicator` 란 viewPager2 같은 스크롤 가능한 콘텐츠에서 사용자가 전체 페이지 중 어디쯤 와 있는지, 총 몇 페이지가 있는지 알려준다.
    - 또 인디케이터를 직접 터치하여 원하는 페이지로 이동 또한 가능하다
    - 점 형태의 인디케이터와 탭 형태의 인디케이터가 있다
  - ViewPager2에서 Indicator를 설정하려면 어떤 과정을 수행해야 하나?

    - `ViewPager2` 와 `TabLayout` 을 같이 생성한다.
    - `TabLayoutMediator` 로 두개를 연결한다.

      ```kotlin
      val viewPager = binding.viewPager
      val tabLayout = binding.tabLayout
      val tabTitles = listOf("수록곡", "상세정보", "영상")

      TabLayoutMediator(tabLayout, viewPager) { tab, position ->
          // 각 탭의 텍스트를 설정합니다.
          tab.text = tabTitles[position]
      }.attach() // attach()를 호출해야 연결이 완료됩니다.
      ```

      - 점모양
      - `res/drawable` 폴더에 `dot_selected.xml`, `dot_default.xml` 를 만든다.
      - `dot_selector.xml` 파일을 추가로 만들어서 선택/기본 시에 점 모양을 지정한다
      - TabLayout의 background로 `dot_selector.xml` 파일을 지정한다.
      - TabLayoutMediator를 사용해 연결한다

      ```kotlin
      TabLayoutMediator(tabLayout, viewPager) { tab, position ->
          // 텍스트를 설정하지 않으면 점 모양만 표시
      }.attach() // attach()를 호출해야 연결이 완료됩니다..
      ```
