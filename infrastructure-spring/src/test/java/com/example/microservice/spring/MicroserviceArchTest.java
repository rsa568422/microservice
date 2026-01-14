package com.example.microservice.spring;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = MicroserviceArchTest.BASE)
class MicroserviceArchTest {

    static final String FRAMEWORK = "org.springframework";
    static final String BASE = "com.example.microservice..";
    static final String DOMAIN = "com.example.microservice.domain..";
    static final String APPLICATION = "com.example.microservice.application..";
    static final String SCHEDULER = "com.example.microservice.scheduler..";
    static final String TASK = "com.example.microservice.task..";
    static final String WORKER = "com.example.microservice.worker..";
    static final String SPRING = "com.example.microservice.spring..";

    @ArchTest
    static final ArchRule FRAMEWORK_FREE_DOMAIN = noClasses()
            .that().resideInAPackage(DOMAIN)
            .or().resideInAPackage(APPLICATION)
            .should().accessClassesThat().resideInAnyPackage(FRAMEWORK)
            .orShould().dependOnClassesThat().resideInAnyPackage(FRAMEWORK);

    @ArchTest
    static final ArchRule DOMAIN_FREE_OF_DEPENDENCIES = noClasses()
            .that().resideInAPackage(DOMAIN)
            .should().accessClassesThat().resideInAnyPackage(APPLICATION, SCHEDULER, TASK, WORKER, SPRING)
            .orShould().dependOnClassesThat().resideInAnyPackage(APPLICATION, SCHEDULER, TASK, WORKER, SPRING);

    @ArchTest
    static final ArchRule APPLICATION_DEPENDENCIES = classes()
            .that().resideInAPackage(APPLICATION)
            .should().accessClassesThat().resideInAPackage(DOMAIN)
            .orShould().dependOnClassesThat().resideInAnyPackage(DOMAIN)
            .orShould().accessClassesThat().resideInAnyPackage(APPLICATION)
            .orShould().dependOnClassesThat().resideInAnyPackage(APPLICATION);
}
