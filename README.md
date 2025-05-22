# 온기 (On:Gi) - 착한 가격업소 소개 앱

**‘온기’는 착한가게 인증 정보를 기반으로 사용자에게 주변 가게를 소개하는 앱입니다.**
<br/>
착한가게 업소 데이터는 Spring Boot 서버를 통해 일일 스케줄링으로 동기화됩니다.

👉 [Play Store에서 다운로드](https://play.google.com/store/apps/details?id=com.aloe_droid.ongi)

---

## 📌 기술 스택

- **언어**: Kotlin 
- **UI**: Jetpack Compose (Material 3)
- **의존성 주입**: Hilt
- **아키텍처**: Clean Architecture + Multi-module
- **비동기 처리**: Kotlin Coroutines
- **데이터 저장소**: Room + Paging3, DataStore Preferences
- **지도/위치**: Naver Map SDK, naver-map-compose, Google Play Services Location
- **이미지 로딩**: Coil 
- **네트워크 통신**: Retrofit2 + OkHttp + kotlinx.serialization
- **로깅**: Timber

---

## 🧱 아키텍처 및 모듈 구성

- **아키텍처 패턴**: MVI (State / SideEffect / Event 기반 단방향 흐름)
- **설계 원칙**: Clean Architecture
- **모듈 구성**:
  - `:app` – 앱 실행, DI 설정, Navigation 구성
  - `:presentation` – UI 계층 (Jetpack Compose 기반 MVI)
  - `:domain` – UseCase, Repository 인터페이스 등 비즈니스 로직 정의
  - `:data` – API, DB 등 실제 데이터 소스 구현 (Repository 구현 포함)

---

## 📁 폴더 구조

###  전체 구조
```
ongi/
├── app/                    
├── data/              
├── domain/                 
└── presentation/      
```
<details>
 <summary>📱 app 모듈 상세 구조</summary>

```
app/
├── src/main/
│   ├── java/com/aloe_droid/ongi/
│   │   └── ui/
│   │       ├── navigation/         # 네비게이션 컴포넌트
│   │       ├── theme/              # 테마 및 디자인 시스템
│   │       ├── MainActivity.kt
│   │       ├── OnGiApp.kt
│   │       └── OnGiApplication.kt
│   ├── res/                       # 리소스 (아이콘, 색상, 문자열 등)
│   └── AndroidManifest.xml
├── src/androidTest/
└── src/test/  
```
</details>

<details> 
<summary>💾 data 모듈 상세 구조</summary>

```
data/
├── src/main/java/com/aloe_droid/data/
│   ├── common/
│   │   ├── di/DispatchersModule.kt
│   │   └── Dispatcher.kt
│   ├── datasource/
│   │   ├── datastore/        # 로컬 데이터 저장소
│   │   │   ├── module/
│   │   │   └── source/
│   │   ├── dto/              # 데이터 전송 객체
│   │   │   ├── banner/
│   │   │   ├── store/
│   │   │   └── user/
│   │   ├── local/            # 로컬 데이터베이스
│   │   │   ├── dao/          # 데이터 액세스 객체
│   │   │   ├── database/
│   │   │   ├── entity/       # 데이터베이스 엔티티
│   │   │   ├── module/
│   │   │   └── util/
│   │   ├── manager/          # 위치 관리자
│   │   │   ├── module/
│   │   │   └── source/
│   │   ├── network/          # 네트워크 통신
│   │   │   ├── api/          # API 인터페이스
│   │   │   ├── module/
│   │   │   ├── source/       # 네트워크 데이터 소스
│   │   │   └── util/
│   │   └── util/
│   └── repository/
│       ├── impl/             # 리포지토리 구현체
│       ├── mapper/           # 데이터 매퍼
│       ├── module/
│       └── page/             # 페이징 처리
├── src/androidTest/
└── src/test/
```
</details>

<details> 
<summary>🎯 domain 모듈 상세 구조</summary>

```
domain/
└── src/main/java/com/aloe_droid/domain/
    ├── entity/               # 도메인 엔티티
    ├── exception/            # 커스텀 예외
    ├── repository/           # 리포지토리 인터페이스
    └── usecase/              # 비즈니스 로직
```
</details>


<details> 
<summary>🎨 presentation 모듈</summary>

```
presentation/
├── src/main/java/com/aloe_droid/presentation/
│   ├── base/                 # 공통 컴포넌트
│   │   ├── component/        # 재사용 가능한 UI 컴포넌트
│   │   ├── theme/            # 디자인 시스템
│   │   └── view/             # 베이스 뷰 클래스
│   ├── filtered_store/       # 필터링된 가게 목록
│   │   ├── component/
│   │   ├── contract/
│   │   ├── data/
│   │   ├── FilteredStoreNavigation.kt
│   │   ├── FilteredStoreScreen.kt
│   │   ├── FilteredStoreState.kt
│   │   └── FilteredStoreViewModel.kt
│   ├── home/                 # 홈 화면
│   │   ├── component/
│   │   ├── contract/
│   │   ├── data/
│   │   ├── HomeNavigation.kt
│   │   ├── HomeScreen.kt
│   │   └── HomeViewModel.kt
│   ├── map/                  # 지도 화면
│   │   ├── component/
│   │   ├── contract/
│   │   ├── data/
│   │   ├── util/
│   │   ├── MapNavigation.kt
│   │   ├── MapScreen.kt
│   │   └── MapViewModel.kt
│   ├── search/               # 검색 화면
│   │   ├── component/
│   │   ├── contract/
│   │   ├── data/
│   │   ├── SearchNavigation.kt
│   │   ├── SearchScreen.kt
│   │   └── SearchViewModel.kt
│   ├── setting/              # 설정 화면
│   │   ├── component/
│   │   ├── contract/
│   │   ├── SettingNavigation.kt
│   │   ├── SettingScreen.kt
│   │   └── SettingViewModel.kt
│   ├── splash/               # 스플래시 화면
│   │   ├── contract/
│   │   └── SplashViewModel.kt
│   └── store/                # 가게 상세 화면
│       ├── component/
│       ├── contract/
│       ├── data/
│       ├── StoreNavigation.kt
│       ├── StoreScreen.kt
│       ├── StoreTopBar.kt
│       └── StoreViewModel.kt
├── src/main/res/
│   ├── drawable/
│   └── values/
├── src/androidTest/
└── src/test/
```
</details>
