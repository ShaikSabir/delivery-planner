plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    kotlin("jvm")
    jacoco
}

group = "org.lucidity.deliveryproject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.dagger:dagger:2.51")
    annotationProcessor("com.google.dagger:dagger-compiler:2.51")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // generate report after tests
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "**/model/**",           // Exclude all model classes
                    "**/Main**",
                    "**/generated/**",             // any generated sources
                    "**/*\$Companion.class",       // Kotlin companion objects
                    "**/*\$*Impl.class",           // Impl suffix (common for generated impls)
                    "**/*Dagger*.*",               // Dagger-generated
                    "**/dagger/**",
                    "**/*_Factory.class",          // Factory classes
                    "**/*_MembersInjector.class",  // Dagger injectors
                    "**/*Builder.class"
                )
            }
        })
    )
}
