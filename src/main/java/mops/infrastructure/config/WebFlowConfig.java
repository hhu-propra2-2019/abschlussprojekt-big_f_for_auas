package mops.infrastructure.config;

import mops.infrastructure.interceptors.WebFlowHandlerAdapterAndInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
@SuppressWarnings("PMD")
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private ITemplateResolver defaultTemplateResolver;

    /**
     * WebFlow muss wissen, wo die Flows liegen. Das wird in dieser Methode konfiguriert.
     * @return für uns unwichtig
     */
    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder() //
                .setBasePath("classpath:flows/definitions") //
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
     * Keine Ahnung, ehrlich gesagt. Hier setzen wir ValidatorFactory, die wir unten konfigurieren.
     * @return keine Ahnung
     */
    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder() //
                .setViewFactoryCreator(this.mvcViewFactoryCreator())
                .setValidator(localValidatorFactoryBean()).build();
    }

    /**
     * Hier wird die MessageSource gesetzt, die wir weiter unten verwenden.
     * @return für uns unwichtig
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(webFlowMessageSource());
        return bean;
    }

    /**
     * Hier konfigurieren wir, wo Spring anhand eines Schlüssels die Nachrichten sucht, die wir in unseren
     * Flows als Fehlermeldungen ausgeben. Wichtig ist vor allem das Encoding, ansonsten könnte man die
     * Dateien auch im Ordner der Flows ablegen.
     * @return ...
     */
    @Bean
    public MessageSource webFlowMessageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:flows/messages/flow-messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
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
     * Die Einstellung setSaveOutputToFlashScopeOnRedirect muss eventuell noch angepasst werden.
     * @return für uns egal
     */
    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new WebFlowHandlerAdapterAndInterceptor();
        handlerAdapter.setFlowExecutor(this.flowExecutor());
        //handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    /**
     * Für uns total egal.
     * @return für uns egal
     */
    @Bean
    public ViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(Collections.singletonList(webFlowViewResolver()));
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }

    /**
     * Wir konfigurieren einen eigenen ViewResolver, um die Templateresolver konfigurieren zu können.
     * @return ...
     */
    @Bean
    public ThymeleafViewResolver webFlowViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(webFlowTemplateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    /**
     * Neben dem TemplateResolver, der in flows/ sucht, benötigen wir noch den standardmäßig konfigurierten,
     * um die Templates für die Menüelemente usw. finden zu können.
     * @return für uns egal
     */
    @Bean
    public SpringTemplateEngine webFlowTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(webFlowTemplateResolver());
        templateEngine.addTemplateResolver(defaultTemplateResolver);
        return templateEngine;
    }

    /**
     * Hier wird konfiguriert, wo Thymeleaf die Templates sucht.
     * @return für uns egal
     */
    @Bean
    public ITemplateResolver webFlowTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("flows/templates/");
        templateResolver.setResolvablePatterns(new HashSet<>(Arrays.asList("fragments/*", "scheduling/*", "poll/*")));
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);
        templateResolver.setOrder(-1);
        return templateResolver;
    }
}
