package mops.adapters.questionpolladapter.converters;

import lombok.NoArgsConstructor;
import mops.adapters.questionpolladapter.dtos.HeaderDto;
import mops.domain.models.questionpoll.QuestionPollHeader;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class HeaderConverter implements Converter<HeaderDto, QuestionPollHeader>  {

    /**
     * QuestionPollHeader converter.
     * @param headerDto das DTO vom WebFlow
     * @return QuestionPollHeader
     */
    @Override
    public QuestionPollHeader convert(HeaderDto headerDto) {
        return new QuestionPollHeader(
                headerDto.getTitle(),
                headerDto.getQuestion(),
                headerDto.getDescription()
        );
    }
}
