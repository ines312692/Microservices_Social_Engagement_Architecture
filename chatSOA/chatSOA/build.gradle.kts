plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "4.3.0.3225"
	jacoco

}

group = "tn.ensit.soa"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2024.0.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		xml.outputLocation.set(file("${buildDir}/reports/jacoco/test/jacocoTestReport.xml"))
		html.required.set(true)
	}
}


tasks.withType<Test> {
	useJUnitPlatform()

	maxParallelForks = Runtime.getRuntime().availableProcessors()
	filter {
		includeTestsMatching("*Test")
		excludeTestsMatching("*IntegrationTest")
	}
}

sonarqube {
	properties {
		property("sonar.projectKey", "ines312692_Microservices_Social_Engagement_Architecture")
		property("sonar.organization", "ines312692")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.sources", "src/main/java")
		property("sonar.tests", "src/test/java")
		property("sonar.junit.reportPaths", "build/test-results/test")
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
	}
}

