package mops.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Collections;

@Configuration
@SuppressWarnings("PMD")
public class WebFlowConfig extends AbstractFlowConfiguration {

    @SuppressWarnings("checkstyle:JavadocVariable")
    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @SuppressWarnings("checkstyle:JavadocVariable")
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * WebFlow muss wissen, wo die Flows liegen. Das wird in dieser Methode konfiguriert.
     * @return für uns unwichtig
     */
    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder() //
                .setBasePath("classpath:flows") //
                .addFlowLocationPattern("/**/*-flow.xml") //
                .setFlowBuilderServices(this.flowBuilderServices()) //
                .build();
    }

    /**
     * Hier wird Spring Security verknüpft, sodass in den .xml-Dateien mit dem Tag „secured“
     * die Zugangsberechtigungen für den jeweiligen Flow festgelegt werden können.
     * @return für uns unwichtig
     */
    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(this.flowRegistry()) //
                .addFlowExecutionListener(new SecurityFlowExecutionListener(), "*")
                .build();
    }

    /**
     * Keine Ahnung, ehrlich gesagt. Irgendwas SpringMvc-internes
     * @return keine Ahnung
     */
    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder() //
                .setViewFactoryCreator(this.mvcViewFactoryCreator())
                .setValidator(this.localValidatorFactoryBean).build();
    }

    /**
     * Hier wird die Reihenfolge definiert, in der Flows gesucht werden. Glaube ich. Total egal für uns.
     * @return für uns egal
     */
    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(this.flowRegistry());
        return handlerMapping;
    }

    /**
     * Die Methode setSaveOutputToFlashScopeOnRedirect muss eventuell noch angepasst werden.
     * @return für uns egal
     */
    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(this.flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    /**
     * Für uns total egal.
     * @return für uns egal
     */
    @Bean
    public ViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(Collections.singletonList(thymeleafViewResolver));
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }

    /**
     * Hier wird konfiguriert, wo Thymeleaf die Templates sucht. Aufgrund von setPrefix("templates/")
     * werden die Templates für den Flow xyz in templates/flows/xyz gesucht.
     * @return für uns egal
     */
    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    /**
     * Die Template-Engine (Thymeleaf) muss für Web FLow manuell gesetzt werden.
     * @return für uns egal
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        return templateEngine;
    }
}
