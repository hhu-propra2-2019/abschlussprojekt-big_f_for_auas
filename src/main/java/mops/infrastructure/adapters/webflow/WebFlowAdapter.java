package mops.infrastructure.adapters.webflow;

import org.springframework.binding.message.MessageContext;

import javax.validation.constraints.NotNull;

public interface WebFlowAdapter<D, O> {

    @NotNull
    D initializeDto();

    boolean validateDto(D dto, MessageContext context);

    @NotNull
    O build(D dto);
}
