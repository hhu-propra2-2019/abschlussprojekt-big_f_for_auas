package mops.infrastructure.adapters.webflow.questionpolladapter.converters;

import lombok.NoArgsConstructor;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.EntryDto;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class EntryConverter implements Converter<EntryDto, QuestionPollEntry> {

    /**
     * QuestionPollEntry converter.
     * @param entryDto das DTO vom WebFlow
     * @return QuestionPollEntry
     */
    @Override
    public QuestionPollEntry convert(EntryDto entryDto) {
        return new QuestionPollEntry(entryDto.getEntry());
    }
}
