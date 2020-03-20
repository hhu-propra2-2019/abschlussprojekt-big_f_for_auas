package mops.adapters.datepolladapter.converters;

import lombok.NoArgsConstructor;
import mops.adapters.datepolladapter.dtos.ConfigDto;
import mops.domain.models.datepoll.DatePollConfig;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class ConfigConverter implements Converter<ConfigDto, DatePollConfig> {

    /**
     * DatePollConfig converter.
     * @param configDto das DTO vom WebFlow
     * @return datePollConfig
     */
    @Override
    public DatePollConfig convert(ConfigDto configDto) {
        return new DatePollConfig(
                configDto.isVoteIsEditable(),
                configDto.isOpenForOwnEntries(),
                configDto.isSingleChoice(),
                configDto.isPriorityChoice(),
                configDto.isAnonymous(),
                configDto.isOpen());
    }
}
