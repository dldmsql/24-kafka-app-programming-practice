
plugins {
    idea
}

dependencies {
    api(project(":adapter-kafka"))
    implementation("org.springframework.boot:spring-boot-starter-mail")
}

tasks.register("prepareKotlinBuildScriptModel") {}



configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

