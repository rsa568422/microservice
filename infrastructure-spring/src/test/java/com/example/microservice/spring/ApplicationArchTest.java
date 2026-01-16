package com.example.microservice.spring;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.mapstruct.Mapper;

import static com.example.microservice.spring.Packages.APPLICATION;
import static com.example.microservice.spring.Packages.DOMAIN;
import static com.example.microservice.spring.Packages.JAVA;
import static com.example.microservice.spring.Packages.SCHEDULER;
import static com.example.microservice.spring.Packages.SPRING;
import static com.example.microservice.spring.Packages.TASK;
import static com.example.microservice.spring.Packages.WORKER;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = APPLICATION)
class ApplicationArchTest {

    private static final String DTO = "com.example.microservice.application.dto";
    private static final String MAPPER = "com.example.microservice.application.mapper";

    @ArchTest
    static final ArchRule APPLICATION_DEPENDENCIES = classes()
            .should().dependOnClassesThat().resideInAnyPackage(DOMAIN, APPLICATION, JAVA);

    @ArchTest
    static final ArchRule APPLICATION_FREE_OF_DEPENDENCIES = noClasses()
            .should().dependOnClassesThat().resideInAnyPackage(SCHEDULER, TASK, WORKER, SPRING);

    @ArchTest
    static final ArchRule APPLICATION_DTO = classes()
            .that().resideInAPackage(DTO)
            .should().beRecords()
            .andShould().accessClassesThat().resideInAPackage(DTO);

    @ArchTest
    static final ArchRule APPLICATION_MAPPERS = classes()
            .that().resideInAPackage(MAPPER).and().areInterfaces()
            .should().beAnnotatedWith(Mapper.class);
}
