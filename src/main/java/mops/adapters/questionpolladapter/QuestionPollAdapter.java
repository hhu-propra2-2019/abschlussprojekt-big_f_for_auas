package mops.adapters.questionpolladapter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.adapters.questionpolladapter.dtos.HeaderDto;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.Validation;
import mops.domain.models.questionpoll.QuestionPollHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@PropertySource(value = "classpath:errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class QuestionPollAdapter {

    private final transient ConversionService conversionService;
    private final transient Environment errorEnvironment;

    @Autowired
    public QuestionPollAdapter(ConversionService conversionService, Environment env) {
        this.conversionService = conversionService;
        this.errorEnvironment = env;
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateHeader(HeaderDto headerDto, MessageContext messageContext) {
        final QuestionPollHeader header = conversionService.convert(headerDto, QuestionPollHeader.class);
        final Validation validation = header.validate();
        mapErrors(validation.getErrorMessages(), messageContext);
        return validation.hasNoErrors();
    }

    private void mapErrors(EnumSet<FieldErrorNames> errors, MessageContext context) {
        errors.forEach(error -> context.addMessage(
                new MessageBuilder()
                        .error()
                        .code(error.toString())
                        .source(errorEnvironment.getProperty(error.toString(), "defaulterrors"))
                        .build()));
    }
}
