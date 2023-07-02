# Demonstration of Elementary in a Gradle build

This is a fairly minimal project to get started with creating an annotation processor for Java,
using [Elementary 2.0](https://github.com/Pante/elementary/tree/stable) for testing.

[Gradle 7](https://gradle.org/) is the build tool, via Gradle Wrapper (`./gradlew`).  
The project has been tested with Gradle 7.5.1, and it should work with Gradle 8.

## Out-of-scope parts

The following is _not_ covered:

* Writing a code-generating annotation processor where the generated code requires runtime code
  or annotations on `@Retention(RUNTIME)`.  
  You'd split such a project into two modules:
    * `foo-runtime`, which contains the runtime artifacts that the generated code expects.
    * `foo-processor`, which contains the annotation processor itself, and any
      annotations that have a `@Retention` other than `RUNTIME`.
* Deployment to Maven Central (or any other repository).
