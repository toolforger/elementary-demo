// build.gradle.kts for elementary-demo

plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.karuslabs:utilitary:2.0.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation("com.karuslabs:elementary:2.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
