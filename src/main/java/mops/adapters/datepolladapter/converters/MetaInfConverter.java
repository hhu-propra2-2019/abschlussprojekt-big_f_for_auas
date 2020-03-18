package mops.adapters.datepolladapter.converters;

import lombok.NoArgsConstructor;
import mops.adapters.datepolladapter.dtos.MetaInfDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class MetaInfConverter implements Converter<MetaInfDto, DatePollMetaInf> {

    /**
     * Konvertiert das MetaInfDto in das eigentliche Objekt.
     *
     * @param metaInfDto das DTO von Web Flow
     * @return das richtige Objekt DatePollMetaInf
     */
    @Override
    @SuppressWarnings({"PMD.LawOfDemeter"})
    /* DTO ist einfacher Datencontainer,reduktion der Kopplung ist hier eher unnötig*/
    public DatePollMetaInf convert(MetaInfDto metaInfDto) {
        DatePollMetaInf datePollMetaInf;
        if (metaInfDto.getStartTime() == null) {
            datePollMetaInf = new DatePollMetaInf(
                    metaInfDto.getTitle(),
                    metaInfDto.getDescription(),
                    metaInfDto.getLocation());

        } else {
            final Timespan timespan = new Timespan(metaInfDto.getStartDate().atTime(metaInfDto.getStartTime()),
                    metaInfDto.getEndDate().atTime(metaInfDto.getEndTime()));
            datePollMetaInf = new DatePollMetaInf(
                    metaInfDto.getTitle(),
                    metaInfDto.getDescription(),
                    metaInfDto.getLocation(),
                    timespan);
        }
        return datePollMetaInf;
    }
}
