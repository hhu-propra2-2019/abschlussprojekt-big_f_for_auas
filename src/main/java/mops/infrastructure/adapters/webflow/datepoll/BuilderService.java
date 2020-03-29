package mops.infrastructure.adapters.webflow.datepoll;

import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.domain.repositories.UserRepository;
import mops.infrastructure.adapters.webflow.ErrorMessageHelper;
import mops.infrastructure.adapters.webflow.PublicationAdapter;
import mops.infrastructure.adapters.webflow.builderdtos.PublicationInformation;
import mops.infrastructure.adapters.webflow.datepoll.builderdtos.Entries;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.DatePollDto;
import mops.infrastructure.controllers.dtos.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
// Alle Fehler sollen bei defaulterrors angezeigt werden, weswegen wir uns eine leere Properties-Datei injecten lassen
@PropertySource(value = "classpath:flows/errormappings/emptymapping.properties", encoding = "UTF-8")
public final class BuilderService {

    private final transient PublicationService publicationService;
    private final transient MetaInfAdapter metaInfAdapter;
    private final transient ConfigAdapter configAdapter;
    private final transient EntriesAdapter entriesAdapter;
    private final transient PublicationAdapter publicationAdapter;
    private final transient Environment errorEnvironment;
    private final transient UserRepository userRepository;

    @Autowired
    public BuilderService(PublicationService publicationService,
                          MetaInfAdapter metaInfAdapter,
                          ConfigAdapter configAdapter,
                          EntriesAdapter entriesAdapter,
                          PublicationAdapter publicationAdapter,
                          Environment errorEnvironment, UserRepository userRepository) {
        this.publicationService = publicationService;
        this.metaInfAdapter = metaInfAdapter;
        this.configAdapter = configAdapter;
        this.entriesAdapter = entriesAdapter;
        this.publicationAdapter = publicationAdapter;
        this.errorEnvironment = errorEnvironment;
        this.userRepository = userRepository;
    }

    public DatePollDto buildConfirmationDto() {
        return new DatePollDto(
                metaInfAdapter.initializeDto(),
                configAdapter.initializeDto(),
                entriesAdapter.initializeDto(),
                publicationAdapter.initializeDto());
    }

    // Zu diesem Zeitpunkt muss jedes Objekt gültig sein, da es nach jedem Schritt in Web Flow validiert wurde.
    // Wenn DatePoll trotzdem nicht gebaut wird, kann das nur an einem Fehler in der Validierung oder Programmlogik
    // liegen. Hier muss von uns nichts mehr überprüft werden. Der Builder überprüft natürlich vor dem Bauen das Objekt.
    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean publishDatePoll(DatePollDto datePollDto,
                                   MessageContext context,
                                   Account account) {
        // TODO: Check, ob User vorhanden (sollte eigentlich nicht nötig sein)
        final User user = userRepository.load(new UserId(account.getName())).get();
        final DatePollMetaInf metaInf = metaInfAdapter.build(datePollDto.getMetaInfDto());
        final Entries entries = entriesAdapter.build(datePollDto.getEntriesDto());
        final PublicationInformation publicationInformation =
                publicationAdapter.build(datePollDto.getPublicationDto());
        // Joar, Law of Demeter. Ist nur ein DTO und darf nicht null sein, also naja.
        datePollDto.getConfigDto().setOpen(publicationInformation.isIspublic());
        final DatePollConfig config = configAdapter.build(datePollDto.getConfigDto());

        final DatePollBuilder builder = new DatePollBuilder();

        builder.creator(user)
                .datePollMetaInf(metaInf)
                .datePollConfig(config)
                .datePollEntries(entries.getEntries())
                .datePollLink(publicationInformation.getLink());
        if (!publicationInformation.isIspublic()) {
            builder.participatingGroups(publicationInformation.getGroups());
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
