package com.example.microservice.spring;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.example.microservice.spring.Packages.APPLICATION;
import static com.example.microservice.spring.Packages.DOMAIN;
import static com.example.microservice.spring.Packages.FRAMEWORK;
import static com.example.microservice.spring.Packages.JAVA;
import static com.example.microservice.spring.Packages.SCHEDULER;
import static com.example.microservice.spring.Packages.SPRING;
import static com.example.microservice.spring.Packages.TASK;
import static com.example.microservice.spring.Packages.WORKER;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = DOMAIN)
class DomainArchTest {

    private static final String MODEL = "com.example.microservice.domain.model..";
    private static final String SERVICE = "com.example.microservice.domain.service..";
    private static final String GENERIC_SERVICE = "com.example.microservice.domain.service.GenericDomainService";
    private static final String OUT_PORT = "com.example.microservice.domain.port.out..";

    @ArchTest
    static final ArchRule DOMAIN_FREE_OF_FRAMEWORK = noClasses()
            .should().dependOnClassesThat().resideInAPackage(FRAMEWORK);

    @ArchTest
    static final ArchRule DOMAIN_FREE_OF_DEPENDENCIES = noClasses()
            .should().dependOnClassesThat().resideInAnyPackage(APPLICATION, SCHEDULER, TASK, WORKER, SPRING);

    @ArchTest
    static final ArchRule DOMAIN_MODELS = classes()
            .that().resideInAPackage(MODEL).and().areNotNestedClasses()
            .should().beTopLevelClasses()
            .andShould().dependOnClassesThat().resideInAnyPackage(MODEL);

    @ArchTest
    static final ArchRule DOMAIN_SERVICES_EXTENDS_GENERIC = classes()
            .that().resideInAPackage(SERVICE)
            .should().beAssignableTo(GENERIC_SERVICE)
            .andShould().dependOnClassesThat().resideInAnyPackage(MODEL, SERVICE, OUT_PORT);

    @ArchTest
    static final ArchRule DOMAIN_REPOSITORIES_ARE_INTERFACES = classes()
            .that().resideInAPackage(OUT_PORT)
            .should().beInterfaces()
            .andShould().dependOnClassesThat().resideInAnyPackage(MODEL, JAVA);
}
