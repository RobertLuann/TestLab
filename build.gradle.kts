plugins {
    id("java")
    id ("application")
    id ("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.ufersa.testlab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.ufersa.testlab.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.7")

    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation("org.hibernate.orm:hibernate-core:7.0.2.Final")

    // https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")

    // https://mvnrepository.com/artifact/commons-validator/commons-validator
    implementation("commons-validator:commons-validator:1.9.0")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}