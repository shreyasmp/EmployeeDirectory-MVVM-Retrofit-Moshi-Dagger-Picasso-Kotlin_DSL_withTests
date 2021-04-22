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
Android Studio: 4.1.3
CompileSDK: 30
MinSDK: 26
TargetSDK: 30
BuildToolsVersion: 29.0.3
GradlePlugin: 4.1.2
Kotlin: 1.4.21
Rest of versions can be found at Versions.kt under /buildSrc/src/main/kotlin/Versions.kt

# Focus Area
My main focus areas were/have been on Test Driven Development 
in MVVM architecture pattern. The architecture used here is very similar
to the one that i use for other side projects outside work and 
also that was introduced by me in current role at work way back in 2018.
Some of the strengths i would say is adopting to Kotlin in recent years
and CoRoutine usage in VM's.  
Testability of most to any part of code or architecture is what the main goal was,
Testing using both mockk and Mockito is also a new learning curve that I brought in
from my side projects here. I believe i have given equal importance to 
UI as well here using largely constraint layout and material designs for Views
Also tried to keep Views as dumb as possible with minimal UI only code
Moving all business logic to Repository or ViewModel
Also added network status repository for WiFi/Cellular connection as a additional app
connectivity check, to make service call on network disconnect and change automatically rather
than killing app and relaunch

# Resources used: 

Project setup was reused from my own side project in github:
https://github.com/shreyasmp/ImgurImageSearch-MVVM-Retrofit-Dagger-Glide-Kotlin_DSL_withTests

At JPM, we specifically only test ViewModels and consider coverage for those 
classes, but in order to test ideally an app, most things should be written
in testable way or be tested. Some extracts for Robolectric Testing for views:
https://www.vogella.com/tutorials/Robolectric/article.html

Some CoRoutine Testing strategies from:  
https://cdmunoz.medium.com/adding-unit-tests-to-an-android-project-that-uses-coroutines-and-livedata-323a4ce53c62

Retrofit/CoRoutine error handling in single place that i had introduced at work and also here:
https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912

# Hardware Focus

Used fragments with activity so this makes is eligible to be used on Tablet's also

# Time Spent

Although this looks like a very small project, but amount of time taken was distributed over
period of 2 days, with 2-3 hrs of work done everyday. I would say it roughly took about 5-6 hrs, 
Since most of the project setup and dependencies setup i brought it as is from my other Github projects

Run Test by coverage shows 60% on current code with Robolectric having know work around with coverage as per below open ticket:
https://github.com/robolectric/robolectric/issues/3023
Edit Configurations: 
Code Coverage Runner -> IntelliJ IDEA
VM Options: -ea -noverify

When tests are run normal, it executes all fine


## Unit Tests Coverage

![alt text](https://github.com/shreyasmp/EmployeeDirectory-MVVM-Retrofit-Moshi-Dagger-Picasso-Kotlin_DSL_withTests/blob/develop/Screen%20Shot%202021-04-22%20at%201.10.32%20AM.png "Unit Tests Coverage")










