package com.example.microservice.spring;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.example.microservice.spring.Packages.APPLICATION;
import static com.example.microservice.spring.Packages.DOMAIN;
import static com.example.microservice.spring.Packages.FRAMEWORK;
import static com.example.microservice.spring.Packages.JAVA;
import static com.example.microservice.spring.Packages.SCHEDULER;
import static com.example.microservice.spring.Packages.TASK;
import static com.example.microservice.spring.Packages.WORKER;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = {SCHEDULER, TASK, WORKER})
class InfrastructureArchTest {

    @ArchTest
    static final ArchRule SCHEDULER_DEPENDENCIES = classes()
            .that().resideInAPackage(SCHEDULER)
            .should().dependOnClassesThat().resideInAnyPackage(DOMAIN, APPLICATION, SCHEDULER, FRAMEWORK, JAVA);

    @ArchTest
    static final ArchRule TASK_DEPENDENCIES = classes()
            .that().resideInAPackage(TASK)
            .should().dependOnClassesThat().resideInAnyPackage(DOMAIN, APPLICATION, TASK, FRAMEWORK, JAVA);

    @ArchTest
    static final ArchRule WORKER_DEPENDENCIES = classes()
            .that().resideInAPackage(WORKER)
            .should().dependOnClassesThat().resideInAnyPackage(DOMAIN, APPLICATION, WORKER, FRAMEWORK, JAVA);
}
