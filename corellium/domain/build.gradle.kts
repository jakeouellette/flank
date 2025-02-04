import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin(Plugins.Kotlin.PLUGIN_JVM)
    kotlin(Plugins.Kotlin.PLUGIN_SERIALIZATION) version Versions.KOTLIN
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += listOf(
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=kotlinx.coroutines.FlowPreview",
    )
}

dependencies {
    implementation(Dependencies.KOTLIN_COROUTINES_CORE)
    api(project(":corellium:api"))
    api(project(":tool:execution:parallel"))
    api(project(":tool:apk"))
    api(project(":tool:filter"))
    api(project(":tool:shard:calculate"))
    api(project(":tool:shard:obfuscate"))
    api(project(":tool:shard:dump"))
    api(project(":tool:instrument:command"))
    api(project(":tool:instrument:log"))
    api(project(":tool:junit"))
    api(project(":tool:log"))
    api(project(":tool:json"))
    api(project(":tool:resource"))
    api(project(Modules.ANALYTICS))
    api(project(Modules.MIXPANEL_ANALYTICS))

    testImplementation(Dependencies.JUNIT)
    testImplementation(project(":corellium:adapter"))
    testImplementation(project(":tool:execution:parallel:plantuml"))
}

tasks.test {
    maxHeapSize = "3072m"
    minHeapSize = "512m"
    dependsOn(":resolveArtifacts")
}
