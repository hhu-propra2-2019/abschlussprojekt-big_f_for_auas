package mops.infrastructure.controllers.dtos;

import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
public class DatePollEntryConverter implements Converter<String, DatePollEntryDto> {


    /** Convertiert eine String id des Timespan Objektes der Form "<LocalDateTime.toString>@<LocalDateTime.toString>"
     * zu einem EntryDto, indem es den String splittet (@ als regex) und dann einzeln parsed mit LocalDateTime.parse().
     *
     * @param id
     * @return
     */
    @Override
    public DatePollEntryDto convert(String id) {
        final String[] timeArr = id.split("@");

        final String strTime1 = timeArr[0];
        final String strTime2 = timeArr[1];

        final LocalDateTime time1 = LocalDateTime.parse(strTime1);
        final LocalDateTime time2 = LocalDateTime.parse(strTime2);

        final Timespan timespan = new Timespan(time1, time2);

        return new DatePollEntryDto(timespan);
    }

}
