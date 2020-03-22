package mops.controllers.dtos;

import mops.domain.models.Timespan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatePollEntryDtoConverter implements Converter<String, DatePollEntryDto> {


    @Override
    public DatePollEntryDto convert(String id) {
        System.out.println(id);
        String[] timeArr = id.split("@");

        String strTime1 = timeArr[0];
        String strTime2 = timeArr[1];

        System.out.println(strTime1);
        System.out.println(strTime2);

        LocalDateTime time1 = LocalDateTime.parse(strTime1);
        LocalDateTime time2 = LocalDateTime.parse(strTime2);


        Timespan timespan = new Timespan(time1, time2);
        System.out.println("-------------------DEBUG-------------");
        return new DatePollEntryDto(timespan);
    }

}
