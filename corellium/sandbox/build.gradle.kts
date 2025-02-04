import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin(Plugins.Kotlin.PLUGIN_JVM)
    // id(Plugins.PLUGIN_SHADOW_JAR) version Versions.SHADOW
}

application {
    mainClassName = "flank.corellium.sandbox.ios.ExampleCorelliumRun"
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

dependencies {
    implementation(Dependencies.KOTLIN_COROUTINES_CORE)
    implementation(project(":corellium:client"))
}

// Tasks below are commented out because are increasing project Gradle build time,
// but are not necessary for current development.

// val runExampleScript by tasks.registering(JavaExec::class) {
//     dependsOn(tasks.build)
//     classpath = sourceSets["main"].runtimeClasspath
//     main = "flank.corellium.sandbox.ios.ExampleCorelliumRun"
// }
//
// val androidExampleJar by tasks.registering(Jar::class) {
//     archiveClassifier.set("all")
//     duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//     archiveBaseName.set("android-example")
//     manifest {
//         attributes("Main-Class" to "flank.corellium.sandbox.android.AndroidExample")
//     }
//     from(
//         configurations.runtimeClasspath
//             .get()
//             .map { if (it.isDirectory) it else zipTree(it) }
//     )
//     from(sourceSets.main.get().output)
// }
//
// val androidExampleNoVPNJar by tasks.registering(Jar::class) {
//     archiveClassifier.set("all")
//     duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//     archiveBaseName.set("android-example-no-vpn")
//     manifest {
//         attributes("Main-Class" to "flank.corellium.sandbox.android.AndroidExampleNoVPN")
//     }
//     from(
//         configurations.runtimeClasspath
//             .get()
//             .map { if (it.isDirectory) it else zipTree(it) }
//     )
//     from(sourceSets.main.get().output)
// }
//
// val runAndroidExample by tasks.registering(Exec::class) {
//     dependsOn(androidExampleJar)
//     workingDir = project.rootDir
//     commandLine("java", "-jar", "./corellium/sandbox/build/libs/android-example-all.jar")
// }
//
// val runAndroidExampleNoVPN by tasks.registering(Exec::class) {
//     dependsOn(androidExampleNoVPNJar)
//     workingDir = project.rootDir
//     commandLine("java", "-jar", "./corellium/sandbox/build/libs/android-example-no-vpn-all.jar")
// }

file("./src/main/resources/corellium-config.properties").also { propFile ->
    if (!propFile.exists() && System.getenv("CI") == null)
        file("./src/main/resources/corellium-config.properties_template").copyTo(propFile)
}
