# e2e-experiment

A companion repository for these 2 blog posts:

* [Lightweight E2E testing for Spring Boot / Angular applications](http://loki2302.me/2017/05/13/Lightweight-E2E-testing-for-Spring-Boot-Angular-applications/)
* [How do Protractor and Angular synchronize?](http://loki2302.me/2017/05/19/How-do-Protractor-and-Angular-synchronize/)

It demonstrates how to put together Spring Boot, Angular and WebDriver and get the basic end-to-end test coverage.

### How to run it

* `./gradlew app:run` to build and run application.
* `./gradlew app:test` to run E2E tests.
* To run for front end development, first do `./gradlew be:run` to run REST APIs and then do `npm start` in `./front-end`.
