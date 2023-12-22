package org.snowcamp.university.springmodulith;

import org.springframework.modulith.core.ApplicationModuleDetectionStrategy;
import org.springframework.modulith.core.JavaPackage;

import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class CustomApplicationModuleDetectionStrategy implements ApplicationModuleDetectionStrategy {

    @Override
    public Stream<JavaPackage> getModuleBasePackages(JavaPackage basePackage) {
        return basePackage.getDirectSubPackages().stream()
                .filter(not(javaPackage -> javaPackage.getName().equals("org.snowcamp.university.springmodulith.common")));
    }
}
