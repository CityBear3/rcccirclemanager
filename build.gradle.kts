plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.5.3"
    id("io.micronaut.test-resources") version "3.5.3"
    id("org.flywaydb.flyway") version "8.5.13"
}

version = "0.1"
group = "dev.postnotifier"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.security:micronaut-security-annotations")

    // micronaut
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-session")
    implementation("io.micronaut.redis:micronaut-redis-lettuce")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari:4.6.3")
    implementation("io.micronaut.openapi:micronaut-openapi")

    // exposed
    implementation("org.jetbrains.exposed", "exposed-core", "0.39.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.39.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.39.1")

    // other
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.postgresql:postgresql")
    compileOnly("org.graalvm.nativeimage:svm")

    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    // test
    testImplementation("org.jeasy:easy-random-core:5.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter")

}


application {
    mainClass.set("dev.postnotifier.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dev.postnotifier.*")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/circle-manager"
    user = "circle-manager"
    password = "circle-manager"
}
