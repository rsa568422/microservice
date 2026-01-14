package com.example.microservice.spring;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.example.microservice.spring.Packages.APPLICATION;
import static com.example.microservice.spring.Packages.DOMAIN;
import static com.example.microservice.spring.Packages.SCHEDULER;
import static com.example.microservice.spring.Packages.SPRING;
import static com.example.microservice.spring.Packages.TASK;
import static com.example.microservice.spring.Packages.WORKER;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = APPLICATION)
class ApplicationArchTest {

    @ArchTest
    static final ArchRule APPLICATION_DEPENDENCIES = classes()
            .should().accessClassesThat().resideInAPackage(DOMAIN)
            .orShould().dependOnClassesThat().resideInAPackage(DOMAIN)
            .orShould().accessClassesThat().resideInAnyPackage(APPLICATION)
            .orShould().dependOnClassesThat().resideInAPackage(APPLICATION);

    @ArchTest
    static final ArchRule APPLICATION_FREE_OF_DEPENDENCIES = noClasses()
            .should().accessClassesThat().resideInAnyPackage(SCHEDULER, TASK, WORKER, SPRING)
            .orShould().dependOnClassesThat().resideInAnyPackage(SCHEDULER, TASK, WORKER, SPRING);
}
