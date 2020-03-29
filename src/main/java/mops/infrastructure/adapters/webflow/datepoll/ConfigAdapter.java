package mops.infrastructure.adapters.webflow.datepoll;

import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollConfig;
import mops.infrastructure.adapters.webflow.WebFlowAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.mapErrors;

@Service
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class ConfigAdapter implements WebFlowAdapter<ConfigDto, DatePollConfig> {

    private final transient Environment errorEnvironment;

    public ConfigAdapter(Environment errorEnvironment) {
        this.errorEnvironment = errorEnvironment;
    }

    @Override
    public ConfigDto initializeDto() {
        final ConfigDto configDto = new ConfigDto();
        configDto.setVoteIsEditable(true);
        configDto.setOpenForOwnEntries(false);
        configDto.setSingleChoice(false);
        configDto.setPriorityChoice(false);
        configDto.setAnonymous(false);
        return configDto;
    }

    public DatePollConfig convert(ConfigDto configDto) {
        return new DatePollConfig(
                configDto.isVoteIsEditable(),
                configDto.isOpenForOwnEntries(),
                configDto.isSingleChoice(),
                configDto.isPriorityChoice(),
                configDto.isAnonymous(),
                configDto.isOpen());
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    public boolean validateDto(ConfigDto configDto, MessageContext context) {
        final Validation validation = convert(configDto).validate();
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @Override
    public DatePollConfig build(ConfigDto configDto) {
        return convert(configDto);
    }
}
