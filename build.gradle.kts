plugins {
    id("org.springframework.boot").version("2.6.2")
    id("io.spring.dependency-management").version("1.0.11.RELEASE")
    id("java")
}

tasks.bootJar {
    archiveBaseName.set("test-result-reporter-api")
    archiveVersion.set("1.0")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("org.springframework.boot", "spring-boot-starter-test")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("org.postgresql", "postgresql")
    implementation("org.modelmapper:modelmapper:2.4.5")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("com.sun.xml.bind:jaxb-impl:2.4.0-b180830.0438")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation("javax.activation:activation:1.1.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:3.0.2")
    implementation("org.json:json:20211205")
    implementation("com.vladmihalcea:hibernate-types-52:2.14.1")
}

tasks.withType<Jar>().all {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

java {
    withJavadocJar()
    withSourcesJar()
}