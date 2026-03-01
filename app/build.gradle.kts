plugins {
    id("java")
    application
    id("com.github.ben-manes.versions") version "0.53.0"
    id("com.diffplug.spotless") version "8.1.0"
    id("org.sonarqube") version "7.1.0.6387"
    checkstyle
//    `java-library`
    jacoco
    id("io.freefair.lombok") version "8.13.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
application {
    mainClass = "hexlet.code.App"
}
repositories {
    mavenCentral()
    gradlePluginPortal()
}

sonar {
    properties {
        property("sonar.projectKey", "rychkov_java-project-72")
        property("sonar.organization", "rychkov")

        // Путь к XML отчету JaCoCo
        property("sonar.coverage.jacoco.xmlReportPaths",
            "${project.layout.buildDirectory.get()}/reports/jacoco/test/jacocoTestReport.xml")
        // Перечисляем пути к отчетам через запятую
        property("sonar.java.checkstyle.reportPaths",
            "${project.layout.buildDirectory.get()}/reports/checkstyle/main.xml")
    }
}

tasks.named<Checkstyle>("checkstyleTest") {
    enabled = false
}

checkstyle {
    toolVersion = "10.12.4"
    // Игнорировать ошибки, чтобы сборка не падала сразу (на ваше усмотрение)
    isIgnoreFailures = false
    // Показывать ошибки в консоли
    isShowViolations = true
}

tasks.named<JavaCompile>("compileJava") {
    options.compilerArgs.add("-Aproject=${project.group}/${project.name}")
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    outputs.upToDateWhen { false }

    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.11"
}
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

tasks.named("sonar") {
    dependsOn(tasks.test)
    dependsOn(tasks.jacocoTestReport)
    dependsOn("checkstyleMain")
}

dependencies {
    implementation("io.javalin:javalin:7.0.1")
    implementation("org.slf4j:slf4j-simple:2.0.17")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:8.1.1")
}
