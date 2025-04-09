# BudgetBuddies BackEnd

**BudgetBuddies**는 개인의 소비 내역을 기록하고, 예산 목표 대비 실제 소비를 분석하는 기능을 제공하는 가계부 백엔드 서비스입니다.  
본 프로젝트는 헥사고날 아키텍처를 기반으로 구조화되어 있으며, 유지보수성과 확장성을 고려한 설계를 지향합니다.

---

## 📦 기능별 설계 및 클래스 구조

각 기능은 **도메인 단위로 명확히 분리**되어 있으며, 모든 흐름은 **유스케이스 중심**으로 작동합니다.  
외부 요청은 `Controller`가 받고, 내부 도메인 로직은 오직 `UseCase`를 통해서만 접근할 수 있습니다.

```
Client → Controller → UseCase (InPort)
                      ↳ OutPort → PersistenceAdapter → Repository
                                   ↳ Domain (Entity, VO, Policy)
```

- **Controller**: 외부 요청을 받고 유스케이스(InPort)를 호출합니다.
- **UseCase**: 핵심 비즈니스 흐름을 담당하며, `Domain` 객체를 사용하고 필요한 경우 `OutPort`를 통해 인프라 접근을 위임합니다.
- **OutPort & Adapter**: UseCase의 출력 포트를 구현한 어댑터로, JPA나 외부 API 등 인프라 기술을 캡슐화합니다.
- **Domain**: 엔티티, 밸류 오브젝트, 도메인 서비스 등 비즈니스 규칙을 표현하며, 오직 UseCase 내부에서만 사용됩니다.

> 이는 의존성 역전 원칙(DIP)에 따른 구조로, 외부 계층은 내부 도메인에 직접 접근하지 않으며, 모든 로직은 유즈케이스를 통해 흐름을 통제합니다.

---

### 1. 사용자 등록

| 계층 | 클래스 | 설명 |
|------|--------|------|
| Controller | `UserController` | 사용자 등록 API (`/users`) |
| UseCase In | `UserUseCase`, `RegisterUserCommand` | 입력 포트 및 커맨드 객체 |
| UseCase Impl | `RegisterUserService` | 유즈케이스 로직 구현체 |
| Adapter Out | `UserPersistenceAdapter`, `UserMapper`, `UserRepository` | 저장 어댑터 및 매퍼 |
| Domain | `User` | 사용자 도메인 모델 |

---

### 2. 소비 목표 설정

| 계층 | 클래스 | 설명 |
|------|--------|------|
| Controller | `ConsumptionGoalController` | 목표 등록 API (`/goals`) |
| UseCase In | `RegisterConsumptionGoalUseCase`, `RegisterConsumptionGoalCommand` | 유즈케이스 입력 |
| UseCase Impl | `RegisterConsumptionGoalService` | 목표 등록 서비스 |
| Adapter Out | `ConsumptionGoalPersistenceAdapter`, `ConsumptionGoalRepository`, `Mapper` | 저장 처리 |
| Domain | `ConsumptionGoal` | 목표 모델 + 월 정보 VO 포함 |

---

### 3. 지출 내역 기록

| 계층 | 클래스 | 설명 |
|------|--------|------|
| Controller | `ExpenseController` | 지출 등록 API (`/expenses`) |
| UseCase In | `RegisterExpenseUseCase`, `RegisterExpenseCommand` | 지출 입력 구조 |
| UseCase Impl | `RegisterExpenseService` | 도메인 모델을 통한 저장 로직 |
| Adapter Out | `ExpensePersistenceAdapter`, `ExpenseRepository` | 지출 저장 |
| Domain | `Expense` | 금액, 날짜, 메모 등 VO 포함 |

---

### 4. 리포트 조회

| 계층 | 클래스 | 설명 |
|------|--------|------|
| Controller | `ReportController` | 리포트 조회 API |
| UseCase In | `ReportUseCase` | 리포트 유즈케이스 |
| UseCase Impl | `GenerateReportService` | 카테고리 별 목표/실적 분석 |
| Domain | `Report` | 리포트 및 항목 구성 |

---

## 🌀 Spring Batch 기반 배치 처리

### ✅ 소비 목표 마이그레이션 배치

**파일**: `ConsumptionGoalBatchAdapter.java`

| 구성 요소 | 클래스/설명 |
|-----------|-------------|
| `Job` | 소비 목표를 기준 날짜로 판단하여 이월 처리 |
| `Reader` | `JpaPagingItemReader`를 사용해 `ConsumptionGoal` 데이터 읽기 |
| `Processor` | 유효기간 지난 목표를 분석하여 신규 이월 목표 생성 |
| `Writer` | `JdbcBatchItemWriter`로 일괄 저장 처리 |
| 실행 방식 | `TaskExecutorJobLauncher`를 통한 비동기 실행 가능 |

```java
JobParameters params = new JobParametersBuilder()
  .addString("requestDate", LocalDateTime.now().toString())
  .toJobParameters();

jobLauncher.run(consumptionGoalMigrationJob, params);
```

> Batch Job 실행은 명시적 호출 또는 스케줄러 연동이 가능합니다.

---

## 🧬 도메인 구성 요약

| 도메인 | 주요 클래스 |
|--------|-------------|
| 사용자 | `User` |
| 목표 | `ConsumptionGoal` |
| 소비 내역 | `Expense` |
| 카테고리 | `Category` |
---

## ⚙ 기술 스택

| 구분 | 내용 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x, Spring Batch |
| Build Tool | Gradle |
| ORM | Spring Data JPA |
| DB | MySQL |
| Architecture | Hexagonal + DDD |
| Test | JUnit 5, Mockito |

---

## 🚀 실행 방법

```bash
git clone https://github.com/your-username/BudgetBuddiesBackEnd.git
cd BudgetBuddiesBackEnd
./gradlew bootRun
```

---

> 이 프로젝트는 구조화된 도메인 모델과 헥사고날 아키텍처를 통해 기능 확장과 유지보수가 용이한 백엔드 구조를 지향합니다.
