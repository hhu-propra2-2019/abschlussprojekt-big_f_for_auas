package mops.adapters.questionpolladapter.converters;

import lombok.NoArgsConstructor;
import mops.adapters.questionpolladapter.dtos.ConfigDto;
import mops.domain.models.questionpoll.QuestionPollConfig;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class ConfigConverter implements Converter<ConfigDto, QuestionPollConfig> {

    /**
     * QuestionPollConfig converter.
     * @param configDto das DTO vom WebFlow
     * @return QuestionPollConfig
     */
    @Override
    public QuestionPollConfig convert(ConfigDto configDto) {
        return new QuestionPollConfig(
                configDto.isSingleChoice(),
                configDto.isAnonymous()
        );
    }
}
