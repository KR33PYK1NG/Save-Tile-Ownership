// ---------- //

val rmcRepoUser: String by project
val rmcRepoPass: String by project

val rmcGroup = "rmc.mixins"
val rmcArtifact = "save-tile-ownership"
val rmcVersion = "1.0.0"
val rmcBaseName = "Save-Tile-Ownership"

setGroup(rmcGroup)
setVersion(rmcVersion)

// ---------- //

apply(plugin = "maven-publish")
apply(plugin = "net.minecraftforge.gradle")
apply(plugin = "org.spongepowered.mixin")

tasks.withType<Jar> {
    setProperty("archiveBaseName", rmcBaseName)
    finalizedBy("reobfJar")
}

tasks.withType<JavaCompile> {
    outputs.upToDateWhen { false }
    setSourceCompatibility("1.8")
    setTargetCompatibility("1.8")
}

configure<PublishingExtension> {
    repositories {
        maven {
            setUrl("https://repo.rus-minecraft.ru/repository/maven")
            credentials {
                setUsername(rmcRepoUser)
                setPassword(rmcRepoPass)
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            setArtifactId(rmcArtifact)
            from(components["java"])
        }
    }
}

configure<net.minecraftforge.gradle.userdev.UserDevExtension> {
    mappings("official", "1.16.5")
}

configure<org.spongepowered.asm.gradle.plugins.MixinExtension> {
    add("main", rmcArtifact + ".refmap.json")
}

// ---------- //

buildscript {
    repositories {
        maven {
            setUrl("https://maven.minecraftforge.net")
        }
        maven {
            setUrl("https://repo.spongepowered.org/maven")
        }
    }
    dependencies {
        add("classpath", "net.minecraftforge.gradle:ForgeGradle:4.1.+")
        add("classpath", "org.spongepowered:mixingradle:0.7-SNAPSHOT")
    }
}

// ---------- //

repositories {
    maven {
        setUrl("https://repo.rus-minecraft.ru/repository/maven")
        credentials {
            setUsername(rmcRepoUser)
            setPassword(rmcRepoPass)
        }
    }
}

dependencies {
    add("minecraft", "net.minecraftforge:forge:1.16.5-36.1.32")
    add("annotationProcessor", "org.spongepowered:mixin:0.8.2:processor")
    add("compileClasspath", "rmc.libs:tile-ownership:1.0.0")
}

// ---------- //