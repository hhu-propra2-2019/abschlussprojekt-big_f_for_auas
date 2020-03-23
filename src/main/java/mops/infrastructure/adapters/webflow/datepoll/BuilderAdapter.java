package mops.infrastructure.adapters.webflow.datepoll;

import mops.application.services.PublicationService;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.UserId;
import mops.infrastructure.adapters.webflow.ErrorMessageHelper;
import mops.infrastructure.adapters.webflow.PublicationAdapter;
import mops.infrastructure.adapters.webflow.datepoll.builderdtos.Entries;
import mops.infrastructure.adapters.webflow.builderdtos.PublicationSettings;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfirmationDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
// Alle Fehler sollen bei defaulterrors angezeigt werden, weswegen wir uns eine leere Properties-Datei injecten lassen
@PropertySource(value = "classpath:flows/errormappings/emptymapping.properties", encoding = "UTF-8")
public final class BuilderAdapter {

    private final transient PublicationService publicationService;
    private final transient MetaInfAdapter metaInfAdapter;
    private final transient ConfigAdapter configAdapter;
    private final transient EntriesAdapter entriesAdapter;
    private final transient PublicationAdapter publicationAdapter;
    private final transient Environment errorEnvironment;

    @Autowired
    public BuilderAdapter(PublicationService publicationService,
                          MetaInfAdapter metaInfAdapter,
                          ConfigAdapter configAdapter,
                          EntriesAdapter entriesAdapter,
                          PublicationAdapter publicationAdapter,
                          Environment errorEnvironment) {
        this.publicationService = publicationService;
        this.metaInfAdapter = metaInfAdapter;
        this.configAdapter = configAdapter;
        this.entriesAdapter = entriesAdapter;
        this.publicationAdapter = publicationAdapter;
        this.errorEnvironment = errorEnvironment;
    }

    public ConfirmationDto buildConfirmationDto(MetaInfDto metaInfDto,
                                                ConfigDto configDto,
                                                EntriesDto entriesDto,
                                                PublicationDto publicationDto) {
        return new ConfirmationDto(metaInfDto, configDto, entriesDto, publicationDto, "");
    }

    // Zu diesem Zeitpunkt muss jedes Objekt gültig sein, da es nach jedem Schritt in Web Flow validiert wurde.
    // Wenn DatePoll trotzdem nicht gebaut wird, kann das nur an einem Fehler in der Validierung oder Programmlogik
    // liegen. Hier muss von uns nichts mehr überprüft werden. Der Builder überprüft natürlich vor dem Bauen das Objekt.
    public boolean publishDatePoll(ConfirmationDto confirmationDto, MessageContext context) {
        DatePollMetaInf metaInf = metaInfAdapter.build(confirmationDto.getMetaInfDto());
        Entries entries = entriesAdapter.build(confirmationDto.getEntriesDto());
        PublicationSettings publicationSettings = publicationAdapter.build(confirmationDto.getPublicationDto());
        // Joar, Law of Demeter. Ist nur ein DTO und darf nicht null sein, also naja.
        confirmationDto.getConfigDto().setOpen(publicationSettings.isIspublic());
        DatePollConfig config = configAdapter.build(confirmationDto.getConfigDto());

        DatePollBuilder builder = new DatePollBuilder();

        builder.creator(new UserId(confirmationDto.getUserId()))
                .datePollMetaInf(metaInf)
                .datePollConfig(config)
                .datePollEntries(entries.getEntries())
                .datePollLink(publicationSettings.getLink());
        if (publicationSettings.isIspublic()) {
            builder.groups(publicationSettings.getGroups());
        }

        final boolean isValid = builder.getValidationState().hasNoErrors();
        if (!isValid) {
            ErrorMessageHelper.addMessage("THIS_SHOULD_BE_VALID", context, errorEnvironment);
            ErrorMessageHelper.mapErrors(builder.getValidationState().getErrorMessages(), context, errorEnvironment);
            return false;
        }
        final boolean hasBeenSaved = publicationService.publishDatePoll(builder.build());
        if (hasBeenSaved) {
            return true;
        }
        ErrorMessageHelper.addMessage("YOU_MOST_DEFINITELY_FUCKED_UP", context, errorEnvironment);
        return false;
    }
}
