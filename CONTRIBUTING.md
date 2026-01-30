# Contributing to Anime Vault

Thank you for your interest in contributing to Anime Vault! We welcome contributions from the community to help make this project better.

## 🛠️ Code Guidelines

To maintain engineering quality, please adhere to the following guidelines:

### Architecture
* Follow **Clean Architecture** principles. Do not leak Android dependencies (Context, Views) into the Domain layer.
* Ensure all data flows are reactive (using `Flow`).
* Keep ViewModels logic-free; delegate business logic to UseCases.

### Style & Formatting
* We use the official **Kotlin Coding Conventions**.
* All UI components must be written in **Jetpack Compose**.
* Use `MaterialTheme` for all colors and typography to ensure dark mode compatibility.

### Pull Requests
1.  Fork the repository and create your branch from `main`.
2.  If you've added code that should be tested, add tests.
3.  Ensure your code builds without warnings.
4.  Format your code using `Ctrl+Alt+L` (Android Studio).
5.  Write a clear, descriptive PR title and description.

## 🐛 Reporting Bugs
Please open an issue and include:
* Device/Emulator model and Android version.
* Steps to reproduce the bug.
* Expected vs. actual behavior.
* Screenshots or screen recordings (if applicable).