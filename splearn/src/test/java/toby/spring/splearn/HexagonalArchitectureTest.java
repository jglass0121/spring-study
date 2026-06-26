package toby.spring.splearn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(
        packages = "toby.spring.splearn",
        importOptions = {
                ImportOption.DoNotIncludeTests.class,
                ImportOption.DoNotIncludeArchives.class
        }
)
public class HexagonalArchitectureTest {
    @ArchTest
    void hexagonalArchitecture(JavaClasses classes) {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy("toby.spring.splearn.domain..")
                .layer("application").definedBy("toby.spring.splearn.application..")
                .layer("adapter").definedBy("toby.spring.splearn.adapter..")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
                .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
                .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
                .check(classes);

     }
}
