package mops.infrastructure.groupsync;

import lombok.NoArgsConstructor;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.infrastructure.groupsync.dto.GroupDto;
import mops.infrastructure.groupsync.dto.GroupSyncInputDto;
import mops.infrastructure.groupsync.dto.GroupSyncValidDto;
import mops.infrastructure.groupsync.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public final class GroupSyncValidator {

    @SuppressWarnings("PMD.LawOfDemeter")
    public GroupSyncValidDto validateAndKeepValid(GroupSyncInputDto dto, Long lastStatus) {
        final GroupSyncValidDto validDto = new GroupSyncValidDto();
        validDto.setStatus(dto.getStatus());
        validDto.setGroups(dto.getGroupList()
                .stream()
                .flatMap(this::validateGroupDto)
                .collect(Collectors.toSet()));
        // Falls ein oder mehrere Gruppen nicht eingelesen werden konnten, Flag setzen
        // Könnte man auch analog zu validateGroupDto lösen, ist so aber eindeutiger.
        if (dto.getGroupList().size() != validDto.getGroups().size()) {
            validDto.setErrorsOccurred(true);
        }
        return validDto;
    }

    // CyclomaticComplexity ist rausgenommen, weil die Guard-Klauseln hier
    // mMn nach nicht sonderlich schwer zu lesen sind.
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.CyclomaticComplexity"})
    private Stream<Group> validateGroupDto(GroupDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()
                || dto.getId() == null || dto.getId().isEmpty()
                || dto.getMembers() == null) {
            return Stream.empty();
        }
        // Nur wegen dieser Guard-Klausel darf unten per ?-Operator entschieden werden!
        if (!("PUBLIC".equals(dto.getVisibility()) || "PRIVATE".equals(dto.getVisibility()))) {
            return Stream.empty();
        }
        final Group group = new Group(
                new GroupId(dto.getId()),
                dto.getTitle(),
                "PUBLIC".equals(dto.getVisibility()) ? Group.GroupVisibility.PUBLIC : Group.GroupVisibility.PRIVATE,
                dto.getMembers().stream().flatMap(this::validateUserDto).collect(Collectors.toSet()));
        // Falls ein oder mehrere User nicht eingelesen werden konnten, Gruppe nicht erzeugen
        if (group.getUser().isEmpty() || dto.getMembers().size() != group.getUser().size()) {
            return Stream.empty();
        }
        return Stream.of(group);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Stream<UserId> validateUserDto(UserDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            return Stream.empty();
        }
        return Stream.of(new UserId(dto.getId()));
    }
}
