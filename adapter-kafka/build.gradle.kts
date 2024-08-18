
plugins {
    idea
}

dependencies {
    api("org.springframework.kafka:spring-kafka")
    api("io.projectreactor.kafka:reactor-kafka")
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

