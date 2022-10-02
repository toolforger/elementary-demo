// build.gradle.kts for elementary-demo

plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.karuslabs.com/repository/elementary-releases")
    }
    maven {
        // Provides annotations-4.9.0-SNAPSHOT, which is requested by the parent pom of the karuslabs dependencies
        url = uri("https://repo.karuslabs.com/repository/chimera-snapshots")
    }
}

dependencies {
    implementation("com.karuslabs:utilitary:1.1.2")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation("com.karuslabs:elementary:1.1.2")
    testImplementation("com.karuslabs:satisfactory:1.1.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
