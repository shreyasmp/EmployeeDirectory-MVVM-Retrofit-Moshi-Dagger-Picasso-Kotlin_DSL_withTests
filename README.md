# EmployeeDirectory-MVVM-Retrofit-Moshi-Dagger-Picasso-Kotlin_DSL_withTests

# This is a MVVM Android App developed using some of latest Android libraries as of 2021

1. Model-View-ViewModel architecture
2. Dagger for Dependency Injection
3. Retrofit for Service with Moshi for Json Parsing
4. Kotlin for development
5. Kotlin DSL for converting gradle scripts from Groovy to Kotlin
6. Coroutines for viewModel handling
7. RecyclerView and CardView for list and individual list item views
8. Picasso Image API for image loading/caching
9. Mockito/Espresso/extJunit/Robolectric for unit testing and android tests for viewmodel, views
10. OKHttp Interceptor/Mockserver for logging service responses on Logcat and creating mock server for testing Repository

# Build Tools and Versions used:
1. Android Studio: 4.1.3
2. CompileSDK: 30
3. MinSDK: 26
4. TargetSDK: 30
5. BuildToolsVersion: 29.0.3
6. GradlePlugin: 4.1.2
7. Kotlin: 1.4.21
8. Rest of versions can be found at Versions.kt under /buildSrc/src/main/kotlin/Versions.kt

Run Test by coverage shows 82% on current code with Robolectric having know work around with coverage as per below open ticket:
https://github.com/robolectric/robolectric/issues/3023
Edit Configurations: 
Code Coverage Runner -> IntelliJ IDEA
VM Options: -ea -noverify

When tests are run normal, it executes all fine


## Unit Tests Coverage

![alt text](https://github.com/shreyasmp/EmployeeDirectory-MVVM-Retrofit-Moshi-Dagger-Picasso-Kotlin_DSL_withTests/blob/develop/Screen%20Shot%202021-04-22%20at%201.10.32%20AM.png "Unit Tests Coverage")

## Unit Tests

![alt text](https://github.com/shreyasmp/EmployeeDirectory-MVVM-Retrofit-Moshi-Dagger-Picasso-Kotlin_DSL_withTests/blob/develop/Screen%20Shot%202021-04-22%20at%201.10.55%20AM.png "Unit Tests")









