plugins {
	java
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.indra"
version = "0.0.1-SNAPSHOT"
description = "redesocial"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.projectlombok:lombok")
    implementation("org.flywaydb:flyway-core:11.9.0")
    implementation("org.flywaydb:flyway-database-postgresql:11.9.0")
    implementation("org.modelmapper:modelmapper:3.2.1")

    runtimeOnly("org.postgresql:postgresql:42.7.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



tasks.withType<Test> {
	useJUnitPlatform()
}
