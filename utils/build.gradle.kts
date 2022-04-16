plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    signing
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 29
        targetSdk = 32
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    exclude("**/R.class", "**/BuildConfig.class")
}



//publishing {
//    publications {
//        register<MavenPublication>("release") {
//            groupId = "com.modestoma"
//            artifactId = "utils-ktx"
//            version = "0.0.1"
//            artifact("")
//            pom {
//                name.set("utils-ktx")
//                description.set("Android Utils")
//                url.set("https://github.com/Modesto1127/android_util_ktx")
//                licenses {
//                    license {
//                        name.set("The Apache License, Version 2.0")
//                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("modesto_ma114")
//                        name.set("modesto_ma114")
//                        email.set("modestoma1127@gmail.com")
//                    }
//                }
//                scm {
//                    connection.set("scm:git@github.com:Modesto1127/android_util_ktx.git")
//                    developerConnection.set("scm:git@github.com:Modesto1127/android_util_ktx.git")
//                    url.set("https://github.com/Modesto1127/android_util_ktx/tree/main")
//                }
//                withXml {
//                    val dependenciesNode = asNode().appendNode("dependencies")
//                    project.configurations.implementation.get().allDependencies.forEach {
//                        val dependencyNode = dependenciesNode.appendNode("dependency")
//                        dependencyNode.appendNode("groupId", it.group)
//                        dependencyNode.appendNode("artifactId", it.name)
//                        dependencyNode.appendNode("version", it.version)
//                    }
//                }
//            }
//        }
//    }
//    repositories {
//        maven {
//            name = "AndroidUtilKtx"
//            val releaseRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
//            val snapshotRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
//            credentials {
//            }
//        }
//    }
//}