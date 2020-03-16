package mops.adapters.datepolladapter.converters;

import mops.adapters.datepolladapter.dtos.MetaInfDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public class MetaInfConverter implements Converter<MetaInfDto, DatePollMetaInf> {

    /**
     * Konvertiert das MetaInfDto in das eigentliche Objekt.
     * @param metaInfDto    das DTO von Web Flow
     * @return              das richtige Objekt DatePollMetaInf
     */
    @Override
    public DatePollMetaInf convert(MetaInfDto metaInfDto) {
        if (metaInfDto.getStartTime() != null) {
            Timespan timespan = new Timespan(metaInfDto.getStartDate().atTime(metaInfDto.getStartTime()),
                                    metaInfDto.getEndDate().atTime(metaInfDto.getEndTime()));
            return new DatePollMetaInf(
                    metaInfDto.getTitle(),
                    metaInfDto.getDescription(),
                    metaInfDto.getLocation(),
                    timespan);
        }
        return new DatePollMetaInf(
                metaInfDto.getTitle(),
                metaInfDto.getDescription(),
                metaInfDto.getLocation());
    }
}
