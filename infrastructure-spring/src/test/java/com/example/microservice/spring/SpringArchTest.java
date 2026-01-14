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

@AnalyzeClasses(packages = SPRING)
class SpringArchTest {

    @ArchTest
    static final ArchRule SPRING_DEPENDENCIES = classes()
            .should().accessClassesThat()
            .resideInAnyPackage(DOMAIN, APPLICATION, SCHEDULER, TASK, WORKER, SPRING, FRAMEWORK, JAVA)
            .orShould().dependOnClassesThat()
            .resideInAnyPackage(DOMAIN, APPLICATION, SCHEDULER, TASK, WORKER, SPRING, FRAMEWORK, JAVA);
}
