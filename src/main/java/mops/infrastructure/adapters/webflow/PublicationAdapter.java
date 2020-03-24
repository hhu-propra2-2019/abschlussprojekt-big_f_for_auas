package mops.infrastructure.adapters.webflow;

import mops.application.services.GroupService;
import mops.domain.models.PollLink;
import mops.domain.models.group.GroupId;
import mops.infrastructure.adapters.webflow.builderdtos.PublicationInformation;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.addMessage;
import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.addMessageWithArguments;

@Service
@PropertySource(value = "classpath:flows/errormappings/publicationmappings.properties", encoding = "UTF-8")
public final class PublicationAdapter implements WebFlowAdapter<PublicationDto, PublicationInformation> {

    private final transient GroupService groupService;
    private final transient Environment errorEnvironment;

    //@Autowired
    public PublicationAdapter(GroupService groupService, Environment env) {
        this.groupService = groupService;
        this.errorEnvironment = env;
    }

    /**
     * TODO: Hier sollte der Link aus dem PublicationService geholt werden.
     *
     * @return ...
     */
    @Override
    public PublicationDto initializeDto() {
        final PublicationDto publicationDto = new PublicationDto();
        publicationDto.setLink("dummylink");
        publicationDto.setIspublic(true);
        publicationDto.setGroups("");
        return publicationDto;
    }

    @Override
    public boolean validateDto(PublicationDto publicationDto, MessageContext context) {
        return validate(publicationDto, context);
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter") //NOPMD
    public PublicationInformation build(PublicationDto publicationDto) {
        return new PublicationInformation(
                publicationDto.isIspublic(),
                new PollLink(publicationDto.getLink()),
                parseGroups(publicationDto.getGroups()).collect(Collectors.toSet()));
    }

    /**
     * Validiert, dass die eingegebenen User und Gruppen existieren und gibt andernfalls passende Fehlermeldungen aus.
     *
     * @param publicationDto ...
     * @param context        Hier werden die Errornachrichten abgelegt
     * @return Gibt zurück, ob in die nächste View gesprungen werden soll oder nicht
     */
    public boolean validate(PublicationDto publicationDto, MessageContext context) {
        if (!publicationDto.isIspublic()) {
            return validateGroupsAndUsers(publicationDto, context);
        }
        return true;
    }

    @SuppressWarnings("PMD.LawOfDemeter")//NOPMD
    private boolean validateGroupsAndUsers(PublicationDto publicationDto, MessageContext context) {
        if (publicationDto.getGroups().isBlank()) {
            addMessage("PUBLICATION_PRIVATE_NO_PARTICIPANTS", context, errorEnvironment);
            publicationDto.setIspublic(true);
            return false;
        }
        final Set<GroupId> invalidGroups = invalidGroups(publicationDto);
        if (!invalidGroups.isEmpty()) {
            mapGroupErrors(invalidGroups, context);
            return false;
        }
        return true;
    }

    // https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
    // https://stackoverflow.com/questions/38698182/close-java-8-stream
    // "Generally, only streams whose source is an IO channel will require closing. Most streams are backed
    // by collections, arrays, or generating functions, which require no special resource management."

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.CloseResource"})
    private Set<GroupId> invalidGroups(PublicationDto publicationDto) {
        return parseGroups(publicationDto.getGroups()).dropWhile(groupService::groupExists).collect(Collectors.toSet());
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Stream<GroupId> parseGroups(String groups) {
        return Arrays.stream(groups.trim().split("\\s*,\\s*")).dropWhile(String::isBlank).map(GroupId::new);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private void mapGroupErrors(Set<GroupId> invalidGroups, MessageContext context) {
        if (invalidGroups.isEmpty()) {
            return;
        }
        addMessageWithArguments(
                "PUBLICATION_GROUPS_NOT_FOUND", context, errorEnvironment,
                invalidGroups.stream().map(GroupId::toString).collect(Collectors.joining(", ")));
    }
}
