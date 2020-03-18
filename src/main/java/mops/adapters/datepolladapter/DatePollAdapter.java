package mops.adapters.datepolladapter;

import mops.adapters.datepolladapter.dtos.MetaInfDto;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

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
    @SuppressWarnings({"PMD.LawOfDemeter"})
    /*
    * Verletzung wird in Kauf genommen um in Validation die entscheidung zu Kapseln wann eine Validierung erfolgreich
    * war, aber die Validierung selbst kann nur das zu validierende Objekt selbst sinvoll lösen
    */
    public boolean validate(MetaInfDto metaInfDto, MessageContext context) {
        final DatePollMetaInf metaInf = conversionService.convert(metaInfDto, DatePollMetaInf.class);
        return metaInf.validate().hasNoErrors();
    }
}
