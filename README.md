📱 CRUD App (Jetpack Compose + Room + Hilt)

A simple offline-first CRUD app built with Jetpack Compose and Room Database, demonstrating modern Android development practices.
The app allows users to create, edit, delete, and manage posts with author details and timestamps.

✨ Features

📝 Create, Edit, and Delete posts

✍️ Each post stores:

Title

Description

Author

Created At & Updated At timestamps

👍👎 Upvote & Downvote system

Unlike YouTube, you can upvote or downvote multiple times deliberately.

Votes are not restricted to toggle between 0 and 1.

This design decision makes it fun to experiment with votes since it’s an offline app.

🎨 Material 3 UI with Jetpack Compose

💉 Hilt for Dependency Injection

🗄️ Room Database for local persistence

🚀 MVVM Architecture with StateFlow for reactive UI updates

🛠️ Tech Stack

Language: Kotlin

UI: Jetpack Compose + Material 3

Architecture: MVVM + Clean Architecture principles

State Management: StateFlow / MutableStateFlow

Database: Room

DI: Hilt

Navigation: Jetpack Navigation Compose

📂 Project Structure
com.crudapp
│
├── data
│   ├── local (Room entities & DAO)
│   ├── repository (PostRepository)
│
├── ui
│   ├── all_posts (List screen)
│   ├── edit (Create/Edit screen)
│   ├── components (Shared Compose UI components)
│
├── di (Hilt modules)
└── MainActivity.kt

🚀 How to Run

Clone this repository:

git clone https://github.com/VishalG2001/CrudRoomdbApp.git


Open in Android Studio (latest version recommended).

Sync Gradle & build the project.

Run on emulator or physical device (minSdk 27, targetSdk 36).
