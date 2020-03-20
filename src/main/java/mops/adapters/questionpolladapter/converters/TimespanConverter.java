package mops.adapters.questionpolladapter.converters;

import lombok.NoArgsConstructor;
import mops.adapters.questionpolladapter.dtos.TimespanDto;
import mops.domain.models.Timespan;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
public class TimespanConverter implements Converter<TimespanDto, Timespan> {

    /**
     * Timespan converter.
     * @param timespanDto das DTO vom WebFlow
     * @return Timespan
     */
    @Override
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public Timespan convert(TimespanDto timespanDto) {
        final LocalDateTime startDate = LocalDate.parse(timespanDto.getStartDate())
                .atTime(LocalTime.parse(timespanDto.getStartTime()));
        final LocalDateTime endDate = LocalDate.parse(timespanDto.getEndDate())
                .atTime(LocalTime.parse(timespanDto.getEndTime()));
        return new Timespan(startDate, endDate);
    }
}
