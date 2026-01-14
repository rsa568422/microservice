package com.example.microservice.spring;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchTest {

    @Test
    public void testArch() {
        final var classes = new ClassFileImporter().importPackages("com.example.microservice");

        final var rule = noClasses()
                .that().resideInAPackage("com.example.microservice.domain..")
                .or().resideInAPackage("com.example.microservice.application..")
                .should().dependOnClassesThat().resideInAnyPackage("org.springframework");

        rule.check(classes);
    }
}
