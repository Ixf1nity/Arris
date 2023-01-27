plugins {
    id("java")
}

group = "me.infinity.arris"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation("redis.clients:jedis:4.3.1")
    implementation("org.slf4j:slf4j-simple:2.0.6")
}