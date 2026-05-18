DermaCare App

A simple Android application built using Clean Architecture + MVVM pattern.
The app demonstrates proper separation of concerns using modern Android architecture.

1. Architecture Overview

This project follows Clean Architecture with 3 main layers:

🔹 Presentation Layer
Activities / UI
ViewModel
LiveData
🔹 Domain Layer
Use Cases
Repository Interfaces
Business Logic
🔹 Data Layer
Repository Implementation
Data Sources (Local / Remote)
Folder Structure
com.example.dermacare
│
├── data
│   └── repository
│       └── WelcomeRepositoryImpl
│
├── domain
│   ├── model
│   │   └── WelcomeMessage
│   ├── repository
│   │   └── WelcomeRepository
│   └── usecase
│       └── GetWelcomeMessageUseCase
│
├── presentation
│   ├── MainActivity
│   └── WelcomeViewModel
Setup Instructions
1. Clone the project
git clone <https://github.com/fkanwal/DermaCare>
2. Open in Android Studio
Open Android Studio
Click “Open Project”
Select folder
3. Build Project
Build → Rebuild Project
4. Run App
Connect emulator or device
Click Run ▶
