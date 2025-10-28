- ListView
  - ListView란 무엇일까요?
    - 안드로이드 앱에서 세로로 스크롤되는 목록을 보여주는 기본적인 UI 위젯이다.
  - ListView에 들어갈 아이템들은 어떻게 저장해야 할까요?
    - 주로 배열이나 ArrayList 같은 리스트 형태의 자료구조에 저장한다.
    - 각 항목이 여러 정보를 담고 있다면 데이터 클래스를 만들고 클래스의 객체를 ArrayList 에 저장하여 사용한다.
  - ListView는 어떤 구성요소로 되어있을까요?
    - ListView, 데이터 원본(Item List), 어탭터

    - ListView는 XML 레이아웃으로 <ListView> 태그를 말하고
    - 데이터 원본은 목록에 표시할 실제 데이터로 앞서 말한 ArrayList나 배열을 말한다.
    - 어댑터는 데이터 원본과 ListView를 연결하는 역할이다.
    - 데이터 원본의 데이터를 하나씩 가져와, 각 항목에 맞는 뷰를 생성하여 ListView에 전달한다.
      - ArrayAdapter(간단한 텍스트), BaseAdapter(커스텀), SimpleAdapter(사진+텍스트 등 여러 데이터 요소의 묶음), CursorAdapter(DB의 데이터를 연결) 등이 있다.
- Adapter
  - Android에서 사용되는 Adapter란 무엇일까요?
    - 데이터와 데이터를 표시할 뷰를 연결하는 다리 역할
  - Adapter는 주로 어떤 역할을 할까요?
    1. 데이터와 뷰의 연결
    2. 뷰 생성 및 재활용
       - 화면에서 사라진 뷰를 버리지 않고 재활용하여 새로운 항목을 표시하는 데 사용한다.
    3. 데이터 관리 및 갱신
       - 리스트의 데이터가 변경되었을 때 `notifyDataSetChanged()` 같은 메서드를 호출하여 화면을 새로고침하도록 ListView에 알려준다
  - ListView의 Adapter는 어떤 구성 요소를 가지고 있을까요?
    1. getCount()
       - 리스트에 표시하 데이터의 총 개수 반환
    2. getItem(int position)
       - 리스트의 특정 position에 해당하는 데이터 반환
    3. getItemId(int position)
       - 각 항목의 고유한 ID 반환, 보통은 항목의 position을 그대로 반환
    4. getView(int position, View convertView, ViewGroup parent)
       - 각 항목에 대한 뷰를 생성하여 반환
       - position: 생성할 항목의 위치
       - convertView: 재활용될 뷰, 없다면 null
       - parent: 이 뷰가 포함되는 부모 뷰 → ListView를 말한다.
- RecyclerView
  - RecyclerView란 무엇일까요?
    - 많은 양의 데이터를 효율적으로 표시하기 위한 뷰
    - 사용자가 화면을 스크롤할 때 화면 밖으로 벗어나는 아이템 뷰를 버리지 않고 재사용하여 새로운 데이터를 표시한다.
  - RecyclerView와 ListView는 어떤 차이점이 있을까요?
    `ListView`
    - 기본적인 재활용 매커니즘
    - 수직 스크롤만 기본 지원
    - 아이템 애니메이션이나 아이템 간의 구분선 등을 직접 구현해야함
    -
    `RecyclerView`
    - ViewHolder 패턴을 사용하여 효율성 극대화
    - 수직, 수평, 그리드 등 다양한 레이아웃을 구현할 수 있다
    - ItemAnimator, ItemDecoration을 통해 애니메이션과 아이템 꾸미기를 할 수 있다
    - 역할이 명확히 분리되어 복잡한 구조를 직접 구현해야 하지만 그만큼 유연하고 확장성이 좋다.
  - RecyclerView Adapter는 어떤 구성 요소를 가지고 있을까요?
    1. onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
       - ViewHolder를 생성한다.
    2. onBindViewHolder(holder: ViewHolder, position: Int)
       - 생성된 ViewHolder에 실제 데이터를 연결한다.
       - position값을 이용하여 데이터 목록에서 해당 데이터를 가져와 ViewHolder 내부의 뷰에 설정한다.
       - 사용자가 스크롤하여 새로운 아이템이 화면에 나올 때마다 호출
    3. getItemCount(): Int
       - RecyclerView에 표시될 전체 아이템 개수 반환
  - RecyclerView를 설정할 때 주의해야 하는 점은 무엇이 있을까요?
    - RecyclerView 의 Adapter는 화면 표시 아이템 개수와 여분 아이템 개수의 ViewHolder를 미리 생성하고, 화면에 표시되지 않는 View를 재사용하는 구조이기 때문에, ViewHolder가 재사용될 때 이전 데이터가 초기화되지 않고 남아있는 문제가 생길 수도 있다.
    - `onBindViewHolder` 는 스크롤할 때마다 자주 호출되므로, ViewHolder 내에서의 복잡한 로직 피하기
    - 아이템의 크기가 동적으로 변하기 않는 경우 `setHasFixedSize(true)` 로 설정하면 내부적으로 크기를 다시 계산할 필요가 없어 성능을 최적화할 수 있다.
  - ViewPager2 에서 사용했던 FragmentStateAdapter와 RecyclerView.Adapter는 어떤 차이가 있을까요?
    - `RecyclerView.Adapter` 는 일반적인 뷰 목록을 처리하기 위한 기본적이고 범용적인 어댑터이다.
    - `FragmentStateAdapter` 는 `RecyclerView.Adapter` 를 상속받아 프래그먼트를 아이템으로 처리하는 데 특화된 어댑터로 프래그먼트의 상태 보존, 생명주기 관리에 특화됨
  - 하나의 리사이클러뷰 안에 여러개의 여러개의 아이템을 넣을 수 있을까요??
    (있다면 방법을, 없다면 안되는 이유도 함께 적어주세요)
    - 하나의 RecyclerView안에 서로 다른 뷰타입의 아이템도 여러 개 표시할 수 있다.
    1. Adapter안에서 `getItemViewType(position: Int)` 메서드 오버라이드하여 position에 따라 각 아이템이 어떤 종류의 뷰인지 나타내는 정수 값을 반환하도록 구현한다.
    2. `onCreateViewHolder()` 에서 뷰 종류에 따라 서로 다른 레이아웃 파일을 인플레이트하고 그에 맞는 Viewholder를 생성하여 반환한다.
    3. `onBindViewHolder()` 에서도 뷰 종류에 따라 각 뷰에 맞는 데이터를 바인딩한다.
