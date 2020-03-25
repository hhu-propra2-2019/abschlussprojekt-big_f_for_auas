package mops.infrastructure.adapters.webflow;

import mops.domain.models.FieldErrorNames;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.core.env.Environment;

import java.util.EnumSet;

public final class ErrorMessageHelper {

    public static final String DEFAULTERRORS = "defaulterrors";

    private ErrorMessageHelper() {
    }

    public static void mapErrors(EnumSet<FieldErrorNames> errors, MessageContext context, Environment errorMappings) {
        errors.forEach(error -> addMessage(error.toString(), context, errorMappings));
    }

    public static void addMessage(String code, MessageContext context, Environment errorMappings) {
        context.addMessage(new MessageBuilder()
                .error()
                .code(code)
                .source(errorMappings.getProperty(code, DEFAULTERRORS))
                .build());
    }

    public static void addMessageWithSource(String code, MessageContext context, String source) {
        context.addMessage(new MessageBuilder()
                .error()
                .code(code)
                .source(source)
                .build());
    }

    public static void addMessageWithArguments(String code,
                                               MessageContext context,
                                               Environment errorMappings,
                                               Object... objects) {
        context.addMessage(new MessageBuilder()
                .error()
                .code(code)
                .source(errorMappings.getProperty(code, DEFAULTERRORS))
                .resolvableArgs(objects)
                .build());
    }
}
