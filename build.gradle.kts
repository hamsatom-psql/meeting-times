plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.findbugs","jsr305", "3.0.2")
    implementation("com.google.code.gson", "gson", "2.8.7")
    implementation("org.junit.jupiter", "junit-jupiter-engine", "5.7.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}