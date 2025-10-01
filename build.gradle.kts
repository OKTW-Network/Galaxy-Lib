import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "2.2.20"
    id("com.gradleup.shadow") version "8.3.9"
}

group = "one.oktw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(kotlin("reflect"))
    api(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.10.2"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    api("org.mongodb:bson:5.6.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget = JvmTarget.JVM_21
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
