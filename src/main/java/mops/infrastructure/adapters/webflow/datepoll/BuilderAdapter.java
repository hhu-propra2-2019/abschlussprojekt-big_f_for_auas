package mops.infrastructure.adapters.webflow.datepoll;

import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.UserId;
import mops.infrastructure.adapters.webflow.ErrorMessageHelper;
import mops.infrastructure.adapters.webflow.PublicationAdapter;
import mops.infrastructure.adapters.webflow.builderdtos.PublicationInformation;
import mops.infrastructure.adapters.webflow.datepoll.builderdtos.Entries;
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
    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean publishDatePoll(ConfirmationDto confirmationDto, MessageContext context) {
        final DatePollMetaInf metaInf = metaInfAdapter.build(confirmationDto.getMetaInfDto());
        final Entries entries = entriesAdapter.build(confirmationDto.getEntriesDto());
        final PublicationInformation publicationInformation = publicationAdapter.build(confirmationDto.getPublicationDto());
        // Joar, Law of Demeter. Ist nur ein DTO und darf nicht null sein, also naja.
        confirmationDto.getConfigDto().setOpen(publicationInformation.isIspublic());
        final DatePollConfig config = configAdapter.build(confirmationDto.getConfigDto());

        final DatePollBuilder builder = new DatePollBuilder();

        builder.creator(new UserId(confirmationDto.getUserId()))
                .datePollMetaInf(metaInf)
                .datePollConfig(config)
                .datePollEntries(entries.getEntries())
                .datePollLink(publicationInformation.getLink());
        if (publicationInformation.isIspublic()) {
            builder.groups(publicationInformation.getGroups());
        }

        final boolean isValid = builder.getValidationState().hasNoErrors();
        if (!isValid) {
            ErrorMessageHelper.addMessage("THIS_SHOULD_BE_VALID", context, errorEnvironment);
            ErrorMessageHelper.mapErrors(builder.getValidationState().getErrorMessages(), context, errorEnvironment);
            return false;
        }
        final boolean hasBeenSaved = publicationService.saveAndPublish(builder.build());
        if (hasBeenSaved) {
            return true;
        }
        ErrorMessageHelper.addMessage("UNKNOWN_DB_ERROR", context, errorEnvironment);
        return false;
    }
}
