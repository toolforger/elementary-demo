# Demonstration of Elementary in a Gradle build

This is a fairly minimal project to get started with creating an annotation
processor for Java.

It uses:

* [Gradle](https://gradle.org/) 7 as a build tool, via Gradle
  Wrapper (`./gradlew`).
  (The project has been tested with Gradle 7.5.1.)
* [Elementary](https://github.com/Pante/elementary/tree/stable) for testing the
  annotation processor.

## Missing pieces

* Some annotation processors generate code that expects to interact with library
  code and/or annotations at runtime.
  You'd split the project into two modules:
    * `foo-runtime` with any required library code and annotations
      on `@Retention(RUNTIME)`.
    * `foo-processor` with the annotation processor itself, and any
      annotations that have a `@Retention` other than `RUNTIME`.
* Deployment to Maven Central (or any other repository) isn't covered.
