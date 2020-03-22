package mops.controllers.dtos;

import mops.domain.models.Timespan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatePollEntryDtoConverter implements Converter<String, DatePollEntryDto> {


    @Override
    public DatePollEntryDto convert(String id) {
        //LocalDateTime times = LocalDateTime.parse(id);
        DatePollEntryDto entryDto = new DatePollEntryDto();
        System.out.println("DEBUG : " + id);
        System.out.println("---------------------------");
        //System.out.println(times);
        return new DatePollEntryDto(new Timespan(LocalDateTime.now(), LocalDateTime.now()));
    }
}
