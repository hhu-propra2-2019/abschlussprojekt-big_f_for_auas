package mops.infrastructure.adapters.webflow.questionpolladapter.converters;

import lombok.NoArgsConstructor;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.HeaderDto;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class HeaderConverter implements Converter<HeaderDto, QuestionPollMetaInf> {

    /**
     * QuestionPollMetaInf converter.
     * @param headerDto das DTO vom WebFlow
     * @return QuestionPollMetaInf
     */
    @Override
    public QuestionPollMetaInf convert(HeaderDto headerDto) {
        return new QuestionPollMetaInf(
                headerDto.getTitle(),
                headerDto.getQuestion(),
                headerDto.getDescription()
        );
    }
}
