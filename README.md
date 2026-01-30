
---
# Anime Vault 📱

**Anime Vault** is a robust, offline-first Android application designed to demonstrate **Modern Android Development (MAD)** practices. It leverages the [Jikan API](https://jikan.moe/) (Unofficial MyAnimeList API) to provide users with a seamless content discovery experience.

This repository serves as a reference implementation for **Clean Architecture**, **Reactive Programming**, and **Production-Grade Error Handling**. The primary engineering goal was to decouple business logic from framework components while ensuring a resilient UI that functions reliably under poor network conditions.

## ✨ Features

### User Experience

* **Discover:** Browse top-rated and trending anime with infinite scrolling.
* **Detailed View:** rich metadata presentation including synopsis, score, and genre.
* **Offline Access:** Full functionality for previously visited data without internet connectivity.
* **Material Design 3:** Adaptive theming with support for Dark/Light modes.

### Technical Highlights

* **Single Source of Truth (SSOT):** The UI observes the local database; the network acts solely as a data source to refresh the cache.
* **Resilient Networking:** Custom handling for API rate limits (HTTP 429) using exponential backoff.
* **Reactive UI:** Fully declarative UI using Jetpack Compose and state hosting.
* **Separation of Concerns:** Strict boundary enforcement between Data, Domain, and Presentation layers.

## 🛠 Tech Stack & Rationale

| Category | Library/Tool | Rationale |
| --- | --- | --- |
| **Language** | Kotlin | Leveraged for its null safety, extension functions, and coroutine support. |
| **UI** | Jetpack Compose | Declarative UI toolkit reduces boilerplate and decouples the view from XML layouts. |
| **Architecture** | Clean Arch + MVVM | Ensures testability and scalability by decoupling business logic from UI and Frameworks. |
| **DI** | Hilt (Dagger) | Compile-time dependency injection for managing the object graph and scope lifecycles. |
| **Async** | Coroutines & Flow | Provides structured concurrency and cold streams for reactive data handling. |
| **Network** | Retrofit + OkHttp | Type-safe HTTP client with interceptors for logging and header management. |
| **Persistence** | Room | Provides an abstraction layer over SQLite with full Flow integration for observability. |
| **Pagination** | Paging 3 | Efficiently handles large datasets with built-in support for `RemoteMediator` (Network + Database). |
| **Image Loading** | Coil | Lightweight, Kotlin-first image loading library supported by Compose. |

## 🏗️ Architecture & Data Flow

The project follows a strict **Clean Architecture** pattern, divided into three distinct layers:

1. **Presentation Layer:** Contains `Components`, `Screens`, and `ViewModels`. The ViewModel maps domain models to `UiState` and exposes a `StateFlow` for the UI to consume.
2. **Domain Layer:** The core business logic containing `UseCases` and `Repository Interfaces`. This layer is pure Kotlin and has no Android dependencies.
3. **Data Layer:** Implements the repository interfaces. It manages data sources (Retrofit for remote, Room for local) and handles the mapping of DTOs to Domain Entities.

### Offline-First Strategy (RemoteMediator)

To ensure a consistent user experience, the app utilizes `RemoteMediator` from Paging 3.

1. **Trigger:** The UI requests data.
2. **Check:** The repository checks the local Room database.
3. **Fetch:** If data is stale or missing, `RemoteMediator` fetches from the Jikan API.
4. **Transaction:** The API response is saved into the database within a transaction.
5. **Observe:** The UI observes the database (SSOT). This ensures that network errors do not crash the UI, as the app falls back to the last known good state.

## 🧠 Engineering Challenges & Solutions

### 1. API Rate Limiting (HTTP 429)

The Jikan API has strict rate limits. To prevent app instability:

* Implemented a centralized `NetworkMonitor` to detect connectivity changes.
* Repository layer intercepts `HTTP 429` errors.
* Implemented a user-facing "Retry" mechanism with exponential backoff rather than infinite automated retries, respecting API civility.

### 2. Unidirectional Data Flow (UDF)

State management is handled using sealed classes (`NetworkResult` and `UiState`).

* **Events** flow up (User interactions -> ViewModel).
* **State** flows down (ViewModel -> UI).
* This prevents "state inconsistency" bugs where the loading indicator might persist after data is loaded.

## 📂 Project Structure

```text
com.shakiv.animevault
├── di                  # Hilt Modules (Network, Database, Repository bindings)
├── data
│   ├── local           # Room DB, DAOs, Entities
│   ├── remote          # API Interfaces, DTOs, Interceptors
│   ├── repository      # Repo Implementations & PagingSource/RemoteMediator
│   └── mapper          # Extension functions for mapping DTO -> Domain -> Entity
├── domain
│   ├── model           # Pure Kotlin data classes
│   ├── repository      # Interfaces defining contract
│   └── usecase         # Reusable business logic (e.g., GetAnimeDetailsUseCase)
├── presentation
│   ├── theme           # Material 3 Theme, Type, Color
│   ├── components      # Shared Composables (AnimeCard, ErrorState, Shimmer)
│   ├── home            # HomeScreen, HomeViewModel, HomeState
│   └── detail          # DetailScreen, DetailViewModel
└── core                # Utility classes, DispatcherProviders, Constants

```

## 🚀 Getting Started

### Prerequisites

* Android Studio Iguana (2023.2.1) or newer.
* JDK 17 target.
* Min SDK 24.

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/shakivhussain/AnimeVault.git

```


2. **Sync Gradle:** Open the project in Android Studio and allow Gradle to fetch dependencies.
3. **Run:** Select the `app` configuration and run on an Emulator (Pixel 6 recommended) or physical device.

*Note: The Jikan API is open and does not require an API Key configuration.*

## 📸 Screenshots

| Light Mode | Dark Mode | Details Screen | Offline/Error State |
| --- | --- | --- | --- |
| <img src="docs/screenshots/home_light.png" width="220"/> | <img src="docs/screenshots/home_dark.png" width="220"/> | <img src="docs/screenshots/detail.png" width="220"/> | <img src="docs/screenshots/offline.png" width="220"/> |

## 🔮 Roadmap & Limitations

### Current Constraints

* **Jikan API:** The free tier occasionally experiences downtime or high latency. The app handles this via error screens, but data availability is dependent on the provider.

### Future Improvements

* [ ] **Testing:** Increase coverage with Unit Tests (JUnit5, Mockk) and UI Tests (Compose Test Rule).
* [ ] **Search:** Implement search functionality with `StateFlow` operators for debouncing.
* [ ] **Modularization:** Refactor into feature modules (`:feature:home`, `:feature:detail`) to decrease build times and enforce separation.
* [ ] **CI/CD:** Setup GitHub Actions for automated linting (KtLint) and testing on PRs.

## 🤝 Contributing

Contributions are welcome. Please ensure that any Pull Request adheres to the project's code style and includes relevant tests. See [CONTRIBUTING.md](https://www.google.com/search?q=CONTRIBUTING.md) for details.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://www.google.com/search?q=LICENSE) file for details.

---

