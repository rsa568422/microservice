package com.example.microservice.spring;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.example.microservice.spring.Packages.APPLICATION;
import static com.example.microservice.spring.Packages.DOMAIN;
import static com.example.microservice.spring.Packages.FRAMEWORK;
import static com.example.microservice.spring.Packages.SCHEDULER;
import static com.example.microservice.spring.Packages.SPRING;
import static com.example.microservice.spring.Packages.TASK;
import static com.example.microservice.spring.Packages.WORKER;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = DOMAIN)
class DomainArchTest {

    @ArchTest
    static final ArchRule DOMAIN_FREE_OF_FRAMEWORK = noClasses()
            .should().accessClassesThat().resideInAPackage(FRAMEWORK)
            .orShould().dependOnClassesThat().resideInAPackage(FRAMEWORK);

    @ArchTest
    static final ArchRule DOMAIN_FREE_OF_DEPENDENCIES = noClasses()
            .should().accessClassesThat().resideInAnyPackage(APPLICATION, SCHEDULER, TASK, WORKER, SPRING)
            .orShould().dependOnClassesThat().resideInAnyPackage(APPLICATION, SCHEDULER, TASK, WORKER, SPRING);
}
