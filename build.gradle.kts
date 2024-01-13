plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "org.snowcamp.university"
version = "latest-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["springModulithVersion"] = "1.1.1"
extra["springdocVersion"] = "2.1.0"
extra["springCloudVersion"] = "2023.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.amqp:spring-rabbit")

	implementation("io.micrometer:micrometer-tracing-bridge-otel")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdocVersion")}")
	implementation("org.springdoc:springdoc-openapi-starter-common:${property("springdocVersion")}")
	implementation("io.swagger:swagger-annotations:1.6.12") // required for open-api generated code

	// TODO STAGE_MODULITH - Uncomment it
//	implementation("org.springframework.modulith:spring-modulith-starter-core")
//	implementation("org.springframework.modulith:spring-modulith-starter-mongodb")
//	implementation("org.springframework.modulith:spring-modulith-events-api")
//	implementation("org.springframework.modulith:spring-modulith-core")
//	testImplementation("org.springframework.modulith:spring-modulith-starter-test")

	runtimeOnly("io.opentelemetry:opentelemetry-exporter-otlp")
	runtimeOnly("io.micrometer:micrometer-registry-otlp")


	// TODO STAGE_MODULITH_OBSERVABILITY - Uncomment it
//	runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
//	runtimeOnly("org.springframework.modulith:spring-modulith-observability")

	// TODO STAGE_MODULITH_EXTERNAL_EVENT - Uncomment it
//	 runtimeOnly("org.springframework.modulith:spring-modulith-events-kafka")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
