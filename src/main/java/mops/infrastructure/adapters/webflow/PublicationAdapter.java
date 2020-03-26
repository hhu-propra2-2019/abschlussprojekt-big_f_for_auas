package mops.infrastructure.adapters.webflow;

import mops.application.services.GroupService;
import mops.domain.models.PollLink;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.infrastructure.adapters.webflow.builderdtos.PublicationInformation;
import mops.infrastructure.adapters.webflow.dtos.GroupSuggestionDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
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

    /*
     * Link ist eine UUID und wird zufällig erzeugt. Falls der Link keine UUID mehr sein sollte,
     * müsste man überlegen, wie auf Kollision geprüft wird oder ob der Link aus einem Service geholt wird.
    */
    @Override
    public PublicationDto initializeDto() {
        final PublicationDto publicationDto = new PublicationDto();
        publicationDto.setLink(new PollLink().getPollIdentifier());
        publicationDto.setIspublic(true);
        publicationDto.setGroups("");
        publicationDto.setSuggestions(new HashSet<>());
        return publicationDto;
    }

    public Set<GroupSuggestionDto> updateSuggestions(UserId userId) {
        return groupService.getValidGroupsForUser(userId).stream()
                .map(group -> new GroupSuggestionDto(group.getId().getId(), group.getTitle()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean validateDto(PublicationDto publicationDto, MessageContext context) {
        if (!publicationDto.isIspublic()) {
            return validateGroups(publicationDto, context);
        }
        return true;
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter") // NOPMD
    public PublicationInformation build(PublicationDto publicationDto) {
        return new PublicationInformation(
                publicationDto.isIspublic(),
                new PollLink(publicationDto.getLink()),
                parseGroups(publicationDto.getGroups()).collect(Collectors.toSet()));
    }

    public boolean validate(PublicationDto publicationDto, MessageContext context) {
        return validateDto(publicationDto, context);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private boolean validateGroups(PublicationDto publicationDto, MessageContext context) {
        if (publicationDto.getLink() == null || publicationDto.getLink().isBlank()) {
            addMessage("PUBLICATION_LINK_BLANK", context, errorEnvironment);
            return false;
        }
        if (publicationDto.getGroups() == null || publicationDto.getGroups().isBlank()) {
            addMessage("PUBLICATION_PRIVATE_NO_PARTICIPANTS", context, errorEnvironment);
            return false;
        }
        final Set<GroupId> invalidGroups = invalidGroups(publicationDto);
        if (!invalidGroups.isEmpty()) {
            mapGroupErrors(invalidGroups, context);
            return false;
        }
        return true;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Set<GroupId> invalidGroups(PublicationDto publicationDto) {
        return parseGroups(publicationDto.getGroups())
                .filter(Predicate.not(groupService::groupExists)).collect(Collectors.toSet());
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Stream<GroupId> parseGroups(String groups) {
        return Arrays.stream(groups.trim().split("\\s*,\\s*"))
                .filter(Predicate.not(String::isBlank)).map(GroupId::new);
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
