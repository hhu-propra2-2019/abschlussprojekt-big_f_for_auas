package mops.archUnit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;


@AnalyzeClasses(packages = "mops")
class archUnitTests {

    @ArchTest
    @ArchIgnore
    static final ArchRule onionArchitectureRule = onionArchitecture()
            .domainModels("mops.domain..")
            .domainServices("mops.domain..")
            .applicationServices("mops.application.services..")
            .adapter("controller", "mops.controllers");

    @ArchTest
    static final ArchRule domain_models_should_not_access_controllers =
            noClasses().that().resideInAPackage("mops.domain..")
            .should().accessClassesThat().resideInAPackage("mops.controllers..");

    @ArchTest
    static final ArchRule domain_models_should_not_access_the_database =
            noClasses().that().resideInAPackage("mops.domain..")
                    .should().accessClassesThat().resideInAPackage("mops.db..");

    @ArchTest
    static final ArchRule domain_models_should_not_access_application_services =
            noClasses().that().resideInAPackage("mops.domain..")
                    .should().accessClassesThat().resideInAPackage("mops.application.services..");

    @ArchTest
    static final ArchRule application_services_should_not_access_controllers =
            noClasses().that().resideInAPackage("mops.application.services..")
                    .should().accessClassesThat().resideInAPackage("mops.controllers..");

    @ArchTest
    static final ArchRule application_services_should_not_access_the_database =
            noClasses().that().resideInAPackage("mops.application.services..")
                    .should().accessClassesThat().resideInAPackage("mops.db..");

    @ArchTest
    static final ArchRule builder_should_only_be_accessed_by_application_Services =
            classes().that().haveNameMatching("DatePollBuilder")
                    .should().onlyBeAccessed().byClassesThat()
                    .resideInAPackage("mops.application.services..");
}
