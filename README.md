# Notes Android App
Easy-to-use and intuintive application for creating notes. Write down your ideas and plans, create shopping lists and etc.

# Development Environment

The app is written entirely in Kotlin and uses the Gradle build system.

To build the app, use the `gradlew build` command or use "Import Project" in
Android Studio. A canary or stable version of Android Studio 4.0 or newer is
required and may be downloaded [here](https://developer.android.com/studio/).

# Screenshots

![Screenshot](https://github.com/Vova-SH/Notes/blob/master/screenshots/main.gif)

# Architecture

The architecture is built around [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

We followed the recommendations laid out in the [Guide to App Architecture](https://developer.android.com/jetpack/docs/guide) when deciding on the architecture for the app. We kept logic away from Activities and Fragments and moved it to [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)s.

We observed data using [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) and used the [Compose](https://developer.android.com/jetpack/compose) to bind UI components in layouts to the app's data sources.

We used [Room](https://developer.android.com/jetpack/androidx/releases/room) for storage and manage your notes.

We used [Koin](https://github.com/InsertKoinIO/koin) for inject ViewModels into Fragments.
