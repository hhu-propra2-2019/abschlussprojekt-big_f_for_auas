package mops.config;

import lombok.NoArgsConstructor;
import mops.adapters.datepolladapter.converters.ConfigConverter;
import mops.adapters.datepolladapter.converters.MetaInfConverter;
import mops.adapters.questionpolladapter.converters.HeaderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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
    }
}
