package mops.adapters;

import mops.adapters.dtos.PublicationDto;
import mops.application.services.GroupService;
import mops.application.services.UserService;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PublicationAdapter {

    private final transient GroupService groupService;
    private final transient UserService userService;

    //@Autowired
    public PublicationAdapter(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    /**
     * Hier sollte der Link aus dem PublicationService geholt werden.
     *
     * @return ...
     */
    public PublicationDto initialize() {
        final PublicationDto publicationDto = new PublicationDto();
        publicationDto.setLink("dummylink");
        return publicationDto;
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
        if (publicationDto.getPeople().isBlank() && publicationDto.getGroups().isBlank()) {
            context.addMessage(new MessageBuilder()
                    .error()
                    .code("PUBLICATION_PRIVATE_NO_PARTICIPANTS")
                    .source("ispublic")
                    .build());
            publicationDto.setIspublic(true);
            return false;
        }
        final Set<UserId> invalidUsers = invalidUsers(publicationDto);
        final Set<GroupId> invalidGroups = invalidGroups(publicationDto);
        if (!invalidUsers.isEmpty() || !invalidGroups.isEmpty()) {
            mapUserErrors(invalidUsers, context);
            mapGroupErrors(invalidGroups, context);
            return false;
        }
        return true;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Set<UserId> invalidUsers(PublicationDto publicationDto) {
        // https://stackoverflow.com/questions/41953388/java-split-and-trim-in-one-shot
        final Stream<String> people =
                Arrays.stream(publicationDto.getPeople().trim().split("\\s*,\\s*")).dropWhile(String::isBlank);
        return people.map(UserId::new).dropWhile(userService::userExists).collect(Collectors.toSet());
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Set<GroupId> invalidGroups(PublicationDto publicationDto) {
        final Stream<String> groups =
                Arrays.stream(publicationDto.getGroups().trim().split("\\s*,\\s*")).dropWhile(String::isBlank);
        return groups.map(GroupId::new).dropWhile(groupService::groupExists).collect(Collectors.toSet());
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private void mapUserErrors(Set<UserId> invalidUsers, MessageContext context) {
        if (invalidUsers.isEmpty()) {
            return;
        }
        context.addMessage(new MessageBuilder()
                .error()
                .code("PUBLICATION_USERS_NOT_FOUND")
                .source("people")
                .resolvableArg(invalidUsers.stream().map(UserId::toString).collect(Collectors.joining(", ")))
                .build());
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private void mapGroupErrors(Set<GroupId> invalidGroups, MessageContext context) {
        if (invalidGroups.isEmpty()) {
            return;
        }
        context.addMessage(new MessageBuilder()
                .error()
                .code("PUBLICATION_GROUPS_NOT_FOUND")
                .source("groups")
                .resolvableArg(invalidGroups.stream().map(GroupId::toString).collect(Collectors.joining(", ")))
                .build());
    }
}
