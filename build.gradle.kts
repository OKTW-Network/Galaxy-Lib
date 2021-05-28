import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "one.oktw"
version = "1.0-SNAPSHOT"

val coroutinesVersion = "1.5.0"
val bsonVersion = "4.2.3"
val log4jVersion = "2.8.1"
val reactivestreamsVersion = "4.2.3"

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(kotlin("reflect"))
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", coroutinesVersion)
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-reactive", coroutinesVersion)
    api("org.mongodb", "bson", bsonVersion)
    api("org.mongodb", "mongodb-driver-reactivestreams", reactivestreamsVersion)
    api("org.apache.logging.log4j", "log4j-slf4j-impl", log4jVersion)
    api("org.apache.logging.log4j", "log4j-core", log4jVersion)

    shadow(kotlin("stdlib-jdk8"))
    shadow(kotlin("reflect"))
    shadow("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)
    shadow("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", coroutinesVersion)
    shadow("org.jetbrains.kotlinx", "kotlinx-coroutines-reactive", coroutinesVersion)
    shadow("org.mongodb", "bson", bsonVersion)
    shadow("org.mongodb", "mongodb-driver-reactivestreams", reactivestreamsVersion)
    shadow("org.apache.logging.log4j", "log4j-slf4j-impl", log4jVersion)
    shadow("org.apache.logging.log4j", "log4j-core", log4jVersion)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

val shadowJar by tasks.getting(ShadowJar::class) {
    archiveClassifier.set("all")
}

val sourcesJar by tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.getByName<Jar>("jar") {
    dependsOn(sourcesJar)
    dependsOn(shadowJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
//            artifact(sourcesJar)
        }
    }
}
