package mops.config;

import lombok.NoArgsConstructor;
import mops.adapters.datepolladapter.converters.ConfigConverter;
import mops.adapters.datepolladapter.converters.MetaInfConverter;
import mops.infrastructure.interceptors.AccountInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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
}
