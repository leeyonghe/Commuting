# Commuting Management / 출퇴근 관리

A Kotlin-based Android application for managing work hours and commuting records.
Kotlin 기반의 출퇴근 시간과 근무 기록을 관리하는 안드로이드 애플리케이션입니다.

## Features / 주요 기능

- **Work Time Recording / 근무 시간 기록**
  - Record start and end times / 출근 및 퇴근 시간 기록
  - Automatic work duration calculation / 근무 시간 자동 계산
  - Overtime notification / 초과 근무 알림

- **Statistics / 통계**
  - Weekly statistics view / 주간 통계 보기
  - Monthly statistics view / 월간 통계 보기
  - Total work hours and days tracking / 총 근무 시간 및 일수 추적
  - Average daily work hours calculation / 일평균 근무 시간 계산

- **Settings / 설정**
  - Customizable standard work hours / 표준 근무 시간 설정
  - Overtime notification settings / 초과 근무 알림 설정
  - Notification permission management / 알림 권한 관리

- **Notifications / 알림**
  - Daily reminder to log departure time / 퇴근 시간 기록 일일 알림
  - Overtime alerts / 초과 근무 알림
  - Android 13+ notification permission support / Android 13+ 알림 권한 지원

## Technical Stack / 기술 스택

- **Language / 언어**: Kotlin
- **Architecture / 아키텍처**: MVVM (Model-View-ViewModel)
- **Database / 데이터베이스**: Room Database
- **Background Tasks / 백그라운드 작업**: WorkManager
- **UI / 사용자 인터페이스**: ViewBinding, ViewPager2
- **Permissions / 권한**: Android 13+ Notification Permission

## Setup Instructions / 설치 방법

1. **Prerequisites / 필수 요구사항**
   - Android Studio (latest version) / Android Studio (최신 버전)
   - Java 17 or higher / Java 17 이상
   - Android SDK (API 33 or higher) / Android SDK (API 33 이상)

2. **Installation / 설치**
   ```bash
   git clone [repository-url]
   cd android_kotlin
   ```

3. **Build Configuration / 빌드 설정**
   - Open the project in Android Studio / Android Studio에서 프로젝트 열기
   - Sync Gradle files / Gradle 파일 동기화
   - Build the project / 프로젝트 빌드

4. **Running the App / 앱 실행**
   - Connect an Android device or start an emulator / Android 기기 연결 또는 에뮬레이터 실행
   - Click "Run" in Android Studio / Android Studio에서 "Run" 클릭

## Usage Guide / 사용 방법

1. **Recording Work Time / 근무 시간 기록**
   - Tap "출근" to record start time / "출근" 버튼을 눌러 출근 시간 기록
   - Tap "퇴근" to record end time / "퇴근" 버튼을 눌러 퇴근 시간 기록
   - Work duration is automatically calculated / 근무 시간이 자동으로 계산됨

2. **Viewing Statistics / 통계 보기**
   - Tap "통계" to view weekly/monthly statistics / "통계" 버튼을 눌러 주간/월간 통계 확인
   - Swipe between weekly and monthly views / 주간과 월간 뷰 사이를 스와이프
   - View total work hours and days / 총 근무 시간과 일수 확인

3. **Settings / 설정**
   - Tap "설정" to access settings / "설정" 버튼을 눌러 설정 화면 진입
   - Set standard work hours / 표준 근무 시간 설정
   - Enable/disable overtime notifications / 초과 근무 알림 활성화/비활성화

## Permissions / 권한

- **POST_NOTIFICATIONS**: Required for Android 13+ to show notifications
  - The app will request this permission on first launch / 앱 첫 실행 시 권한을 요청합니다
  - Can be managed in device settings / 기기 설정에서 관리할 수 있습니다

## Contributing / 기여 방법

1. Fork the repository / 저장소를 포크합니다
2. Create a feature branch / 기능 브랜치를 생성합니다
3. Commit your changes / 변경사항을 커밋합니다
4. Push to the branch / 브랜치에 푸시합니다
5. Create a Pull Request / 풀 리퀘스트를 생성합니다

## License / 라이센스

This project is licensed under the MIT License - see the LICENSE file for details.
이 프로젝트는 MIT 라이센스 하에 배포됩니다 - 자세한 내용은 LICENSE 파일을 참조하세요.

## Project Structure / 프로젝트 구조

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── aos_kotlin/
│   │   │           ├── MainActivity.kt
│   │   │           ├── SettingsActivity.kt
│   │   │           ├── AOSApplication.kt
│   │   │           ├── model/
│   │   │           │   └── WorkRecord.kt
│   │   │           ├── database/
│   │   │           │   ├── AppDatabase.kt
│   │   │           │   ├── WorkRecordDao.kt
│   │   │           │   └── converter/
│   │   │           │       └── DateConverter.kt
│   │   │           ├── preference/
│   │   │           │   └── WorkPreference.kt
│   │   │           └── worker/
│   │   │               └── NotificationWorker.kt
│   │   └── res/
│   │       ├── layout/
│   │       │   ├── activity_main.xml
│   │       │   ├── activity_settings.xml
│   │       │   ├── fragment_weekly_stats.xml
│   │       │   └── fragment_monthly_stats.xml
```

## Data Model / 데이터 모델

### WorkRecord
- id: Record ID / 기록 ID
- date: Work date / 근무 날짜
- startTime: Start time / 출근 시간
- endTime: End time / 퇴근 시간
- workDuration: Work duration (in minutes) / 근무 시간 (분 단위)

## Database / 데이터베이스

Room database is used to store work records.
Room 데이터베이스를 사용하여 근무 기록을 저장합니다.

### Main Queries / 주요 쿼리
- Daily work record query / 일별 근무 기록 조회
- Period-based work record query / 기간별 근무 기록 조회
- Add/Modify work records / 근무 기록 추가/수정

## Development Environment / 개발 환경

- Android Studio
- Kotlin 1.8
- Android SDK 34
- Gradle 8.0
- Java 17

