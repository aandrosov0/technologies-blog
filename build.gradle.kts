val h2Version: String by project
val ktorVersion: String by project
val exposedVersion: String by project
val slf4jSimpleVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    application
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-freemarker:$ktorVersion")

    implementation("com.h2database:h2:$h2Version")

    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
}