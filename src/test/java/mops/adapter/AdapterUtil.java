package mops.adapter;

import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public final class AdapterUtil {

    public static MessageContext getMessageContext() {
        final ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:flows/messages/flow-messages");
        messageSource.setDefaultEncoding("UTF-8");
        return new DefaultMessageContext(messageSource);
    }
}
