package mops.infrastructure.adapters.webflow.datepoll;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollConfig;
import mops.infrastructure.adapters.webflow.WebFlowAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.mapErrors;

@Service
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class ConfigAdapter implements WebFlowAdapter<ConfigDto, DatePollConfig> {

    private final transient ConversionService conversionService;
    private final transient Environment errorEnvironment;

    public ConfigAdapter(ConversionService conversionService, Environment errorEnvironment) {
        this.conversionService = conversionService;
        this.errorEnvironment = errorEnvironment;
    }

    @Override
    public ConfigDto initializeDto() {
        return new ConfigDto();
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null-Referenz zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    public boolean validateDto(ConfigDto configDto, MessageContext context) {
        final DatePollConfig config = conversionService.convert(configDto, DatePollConfig.class);
        final Validation validation = config.validate();
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @Override
    public DatePollConfig build(ConfigDto configDto) {
        return conversionService.convert(configDto, DatePollConfig.class);
    }
}
