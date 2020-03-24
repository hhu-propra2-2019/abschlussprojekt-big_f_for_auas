package mops.infrastructure.adapters.webflow.datepoll;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.domain.models.PollFields;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.infrastructure.adapters.webflow.WebFlowAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.mapErrors;

@Service
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class MetaInfAdapter implements WebFlowAdapter<MetaInfDto, DatePollMetaInf> {

    private final transient Environment errorEnvironment;
    private final transient ConversionService conversionService;

    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public MetaInfAdapter(Environment errorEnvironment, ConversionService conversionService) {
        this.errorEnvironment = errorEnvironment;
        this.conversionService = conversionService;
    }

    /**
     * Validiert MetaInfDto, nachdem Titel und evtl. Beschreibung und Ort eingegeben wurden.
     *
     * @param metaInfDto ...
     * @param context    im MessageContext können die Fehlermeldungen angehängt werden
     * @return ob die Transition in den nächsten State stattfinden soll oder nicht
     */
    @SuppressWarnings("PMD.LawOfDemeter")//NOPMD
    /*
     * Verletzung wird in Kauf genommen um in Validation die entscheidung zu Kapseln wann eine Validierung erfolgreich
     * war, aber die Validierung selbst kann nur das zu validierende Objekt selbst sinvoll lösen
     */
    public boolean validateFirstStep(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = validate(metaInfDto)
                .removeErrors(PollFields.TIMESPAN);
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    private Validation validate(MetaInfDto metaInfDto) {
        return conversionService.convert(metaInfDto, DatePollMetaInf.class).validate();
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    public MetaInfDto initializeDto() {
        return new MetaInfDto("", "", "",
                LocalDate.now().toString(),
                LocalTime.now().format(formatter),
                LocalDate.now().plusMonths(1).toString(),
                LocalTime.now().format(formatter));
    }

    @Override
    @SuppressWarnings({"PMD.LawOfDemeter"})
     public boolean validateDto(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = validate(metaInfDto);
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @Override
    public DatePollMetaInf build(MetaInfDto metaInfDto) {
        return conversionService.convert(metaInfDto, DatePollMetaInf.class);
    }
}
