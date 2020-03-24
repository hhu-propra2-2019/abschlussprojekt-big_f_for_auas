package mops.infrastructure.adapters.webflow.datepoll.converters;

import lombok.NoArgsConstructor;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        if (metaInfDto.getStartDate() == null) {
            datePollMetaInf = new DatePollMetaInf(
                    metaInfDto.getTitle(),
                    metaInfDto.getDescription(),
                    metaInfDto.getLocation());
        } else {
            datePollMetaInf = new DatePollMetaInf(
                    metaInfDto.getTitle(),
                    metaInfDto.getDescription(),
                    metaInfDto.getLocation(),
                    parseTime(metaInfDto));
        }
        return datePollMetaInf;
    }

    // TODO: add error handling
    @SuppressWarnings("PMD.LawOfDemeter")
    private Timespan parseTime(MetaInfDto metaInfDto) {
        final LocalDateTime startDate = LocalDate.parse(metaInfDto.getStartDate())
                .atTime(LocalTime.parse(metaInfDto.getStartTime()));
        final LocalDateTime endDate = LocalDate.parse(metaInfDto.getEndDate())
                .atTime(LocalTime.parse(metaInfDto.getEndTime()));
        return new Timespan(startDate, endDate);
    }
}
