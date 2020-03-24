package mops.infrastructure.config;

import lombok.NoArgsConstructor;
import mops.infrastructure.adapters.webflow.datepoll.converters.ConfigConverter;
import mops.infrastructure.adapters.webflow.datepoll.converters.MetaInfConverter;
import mops.infrastructure.adapters.webflow.questionpolladapter.converters.EntryConverter;
import mops.infrastructure.adapters.webflow.questionpolladapter.converters.HeaderConverter;
import mops.infrastructure.adapters.webflow.questionpolladapter.converters.TimespanConverter;
import mops.infrastructure.interceptors.AccountInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@NoArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Registriert Formatter und Converter. Wenn für das Befüllen der DTOs bzw. das Einlesen der DTOs
     * in Converter verlegt werden soll, dann müssen diese hier registriert werden.
     * @param registry für uns unwichtig
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new MetaInfConverter());
        registry.addConverter(new ConfigConverter());
        registry.addConverter(new HeaderConverter());
        registry.addConverter(new TimespanConverter());
        registry.addConverter(new mops.infrastructure.adapters
                .webflow.questionpolladapter.converters.ConfigConverter());
        registry.addConverter(new EntryConverter());
    }

    /**
     * Hier wird ein Interceptor hinzugefügt, der bei jeder Request aufgerufen wird.
     * Der Interceptor fügt ein Account-Objekt als Attribut „account“ in das Modell ein,
     * falls ein(e) Nutzer*in eingeloggt ist
      * @param registry ...
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccountInterceptor());
    }

    /**
     * Hier wird ein Filter für Web Flow registriert. Dadurch werden die User auch bei Flow-URLs aufgefordert,
     * sich einzuloggen. Andernfalls würde ein Error geworfen. Leider wird der Status der Flows aber nicht
     * über verschiedene Login-Sessions hinweg aufrechterhalten. Das bedeutet, dass nach einem Timeout des Logins
     * von Neuem mit der Erstellung von einer Terminfindung oder Abstimmung begonnen werden muss.
     * @return ...
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> someFilterRegistration() {
        final FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        registration.setFilter(new DelegatingFilterProxy());
        registration.addUrlPatterns("/*");
        registration.setName("springSecurityFilterChain");
        return registration;
    }
}
