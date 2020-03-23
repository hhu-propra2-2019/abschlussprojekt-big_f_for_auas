package mops.infrastructure.adapters.webflow;

import org.springframework.binding.message.MessageContext;

import javax.validation.constraints.NotNull;

public interface WebFlowAdapter<DTO, OBJECT> {

    @NotNull
    DTO initializeDto();

    boolean validateDto(DTO dto, MessageContext context);

    @NotNull
    OBJECT build(DTO dto);
}
