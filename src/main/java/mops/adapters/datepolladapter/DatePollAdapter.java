package mops.adapters.datepolladapter;

import mops.adapters.datepolladapter.dtos.MetaInfDto;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class DatePollAdapter {

    private final ConversionService conversionService;

    @Autowired
    public DatePollAdapter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public boolean validate(MetaInfDto metaInfDto, MessageContext context) {
        DatePollMetaInf metaInf = conversionService.convert(metaInfDto, DatePollMetaInf.class);
        return metaInf.validate().hasNoErrors();
    }
}
