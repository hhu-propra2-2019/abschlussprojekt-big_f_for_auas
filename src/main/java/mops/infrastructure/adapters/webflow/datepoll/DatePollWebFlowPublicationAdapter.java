package mops.infrastructure.adapters.webflow.datepoll;

import lombok.NoArgsConstructor;
import mops.domain.models.datepoll.DatePoll;
import mops.infrastructure.adapters.webflow.datepoll.dtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.ConfirmationDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.MetaInfDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public final class DatePollWebFlowPublicationAdapter {

    /*private final transient PublicationService publicationService;

    @Autowired
    public DatePollWebFlowPublicationAdapter(PublicationService publicationService) {
        this.publicationService = publicationService;
    }*/

    // TODO: Implementieren, offensichtlich

    public ConfirmationDto buildConfirmationDto(MetaInfDto metaInfDto,
                                                ConfigDto configDto,
                                                EntriesDto entriesDto,
                                                PublicationDto publicationDto) {
        return null;
    }

    public DatePoll publishDatePoll(ConfirmationDto confirmationDto) {
        return null;
    }
}
