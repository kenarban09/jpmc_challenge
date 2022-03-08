# Coding Exercise - JP Morgan

Create an Android app that makes use of the following API:

Api - https://jsonplaceholder.typicode.com/
List of albums: https://jsonplaceholder.typicode.com/albums

The minimum viable product;
- Application should have screen which displays albums list sorted by
title
- Albums should be persisted for offline viewing

Requirements;
- Use the latest recommendations from Android Community for
architecture approach and libraries.
- MinSDK Version is 23
- Keep an eye on performance
- Application should be unit tested and instrumentation tested
- Upload the code in Github
- Provide a list of recommendations for future improvements

## Future improvements

* Move sensitive data to `gradle.properties` local file, outside the project, for improve security stuffs
* Migrate to Jetpack Compose
* Improve concurrency logic in Coroutine Flow (using map or flatMap operators)
* Implement Dagger Hilt as Dependency Injection framework
* Implement Continuous Integration plugins such as Detekt, SonarQube
* Implement Code coverage with Jacoco
* Implement UI Test with Espresso.