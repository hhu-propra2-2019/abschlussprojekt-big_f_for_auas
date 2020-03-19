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
        System.out.println("VoteIsEditable: " + configDto.isVoteIsEditable());
        System.out.println("OpenForOwnEntries: " + configDto.isOpenForOwnEntries());
        System.out.println("SingleChoice: " + configDto.isSingleChoice());
        System.out.println("PriorityChoice: " + configDto.isPriorityChoice());
        System.out.println("Anonymous: " + configDto.isAnonymous());
        System.out.println("Open: " + configDto.isOpen());

        return new DatePollConfig(configDto.isVoteIsEditable(),
                configDto.isOpenForOwnEntries(),
                configDto.isSingleChoice(),
                configDto.isPriorityChoice(),
                configDto.isAnonymous(),
                configDto.isOpen());
    }
}
