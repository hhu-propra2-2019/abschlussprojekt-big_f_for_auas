package mops.adapters.datepolladapter;

import mops.adapters.datepolladapter.dtos.ConfigDto;
import mops.adapters.datepolladapter.dtos.MetaInfDto;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class DatePollAdapter {

    private final transient ConversionService conversionService;

    @Autowired
    public DatePollAdapter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Validiert MetaInfDto, nachdem Titel und evtl. Beschreibung und Ort eingegeben wurden.
     * @param metaInfDto ...
     * @param context im MessageContext können die Fehlermeldungen angehängt werden
     * @return ob die Transition in den nächsten State stattfinden soll oder nicht
     */
    public boolean validate(MetaInfDto metaInfDto, MessageContext context) {
        DatePollMetaInf metaInf = conversionService.convert(metaInfDto, DatePollMetaInf.class);
        Validation validation = metaInf.validate();
        mapErrors(validation.getErrorMessages(), context);
        return validation.hasNoErrors();
    }

    public boolean validate(ConfigDto configDto, MessageContext context) {
        return false;
    }

    private void mapErrors(EnumSet<FieldErrorNames> errors, MessageContext context) {
        errors.forEach(error -> context.addMessage(
                new MessageBuilder()
                        .error()
                        .code(error.getKey())
                        .source("title")
                        .build()));
    }
}
