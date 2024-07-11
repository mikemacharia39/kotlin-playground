# kotlin-playground
This code implements clean architecture.
Clean architecture is a software design philosophy that separates the elements of a design into ring levels. 
The main rule of clean architecture is that code dependencies can only come from the outer levels inward. 
The outermost level is the most abstract and contains the business logic. 
As you move inward, you get more concrete implementations.

## Some code snippets to learn Kotlin

### What is contained in this repository?
1. This repo uses Gradle
2. It is written in Kotlin
3. It uses Java 17
4. It uses Spring boot 3.2.1
5. It uses JUnit 5
6. It uses kotest
7. Kotlin faker to generate faked data

### Features
1. It has a simple REST API to manage employees
2. Create employee
3. Bulk create employees
4. The bulk upload uses apache poi to read from an excel file
5. The upload uses coroutines to upload in parallel
6. The upload uses kotlin flows
