package toby.spring.learningtest.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "toby.spring.learningtest.archunit")
public class ArchUnitLearningTest {

    /**
     * Application 클래스를 의존하는 클래스는 application, adapter에만 존재해야 한다.
     */

    @ArchTest
    void application(JavaClasses classes) {
        classes().that().resideInAnyPackage("..application..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
                .check(classes);
    }

    /**
     * Application클래스는 adapter의 클래스를 의존하면 안된다
     */
    @ArchTest
    void adapter(JavaClasses classes) {
        noClasses().that().resideInAnyPackage("..application..")
                .should().dependOnClassesThat().resideInAnyPackage("..adapter..")
                .check(classes);
    }

    /**
     * Domain의 클래스는 domain, java
     */

    @ArchTest
    void domain(JavaClasses classes) {
        classes().that().resideInAnyPackage("..domain..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..domain..", "..adapter..", "..java..")
                .check(classes);

    }
}
