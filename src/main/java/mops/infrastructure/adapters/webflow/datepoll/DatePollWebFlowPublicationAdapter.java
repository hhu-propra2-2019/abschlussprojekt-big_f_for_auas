package mops.infrastructure.adapters.webflow.datepoll;

import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePoll;
import mops.infrastructure.adapters.webflow.datepoll.dtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.ConfirmationDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.MetaInfDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class DatePollWebFlowPublicationAdapter {

    private final transient PublicationService publicationService;

    @Autowired
    public DatePollWebFlowPublicationAdapter(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public ConfirmationDto buildConfirmationDto(MetaInfDto metaInfDto,
                                                ConfigDto configDto,
                                                EntriesDto entriesDto,
                                                PublicationDto publicationDto) {
        return new ConfirmationDto(metaInfDto, configDto, entriesDto, publicationDto);
    }

    public DatePoll publishDatePoll(ConfirmationDto confirmationDto) {
        return null;
    }
}
