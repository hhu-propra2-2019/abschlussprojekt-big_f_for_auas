package mops.infrastructure.groupsync;

import lombok.NoArgsConstructor;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.domain.models.user.UserId;
import mops.infrastructure.groupsync.dto.GroupDto;
import mops.infrastructure.groupsync.dto.JacksonInputDto;
import mops.infrastructure.groupsync.dto.ValidatedInputDto;
import mops.infrastructure.groupsync.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public final class GroupSyncValidator {

    @SuppressWarnings("PMD.LawOfDemeter") //NOPMD
    public ValidatedInputDto validateAndKeepValid(JacksonInputDto dto) {
        final Set<ValidatedGroup> validatedGroups =
                dto.getGroupList().stream().flatMap(this::validateGroups).collect(Collectors.toSet());
        final ValidatedInputDto validDto = new ValidatedInputDto();
        validDto.setStatus(dto.getStatus());
        validDto.setGroups(
                validatedGroups.stream()
                        .filter(ValidatedGroup::isValid)
                        .collect(Collectors.toSet()));
        validDto.setDeletedGroups(
                validatedGroups.stream()
                        .filter(ValidatedGroup::isDeleted)
                        .map(group -> group.getMetaInf().getId())
                        .collect(Collectors.toSet()));
        // Falls ein oder mehrere Gruppen nicht eingelesen werden konnten, Flag setzen
        // Könnte man auch analog zu validateGroupDto lösen, ist so aber eindeutiger.
        if (dto.getGroupList().size() != validDto.getChangedGroupCount()) {
            validDto.setErrorsOccurred(true);
        }
        return validDto;
    }

    /**
     * Entscheidet, ob die Gruppe gültig ist oder eine gelöschte Gruppe (= alle Felder außer ID sind null).
     * Außerdem kann die Gruppe ungültig sein, d.h. die ID ist leer oder null und die anderen Felder sind
     * nur teilweise gesetzt. Dann wird ein leerer Stream zurückgegeben, der mit flatMap() unkompliziert
     * aussortiert werden kann.
     * @param dto Das Dto, das entweder eine gültige, ungültige oder gelöschte Gruppe sein kann.
     * @return das entsprechende Objekt
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    private Stream<ValidatedGroup> validateGroups(GroupDto dto) {
        if (isDeleted(dto)) {
            return Stream.of(
                    new DeletedGroup(new GroupId(dto.getId())));
        }
        if (isValid(dto)) {
            final GroupVisibility visibility =
                    "PUBLIC".equals(dto.getVisibility()) ? GroupVisibility.PUBLIC : GroupVisibility.PRIVATE;
            return Stream.of(
                    new ValidGroup(
                            new GroupMetaInf(
                                    new GroupId(dto.getId()), dto.getTitle(), visibility),
                            // TODO: Overhead beseitigen: User werden zwei Mal geparsed. Eventuell ändern.
                            dto.getMembers()
                                    .stream().flatMap(this::validateUserDto).collect(Collectors.toSet())));
        }
        return Stream.empty();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private boolean isDeleted(GroupDto dto) {
        return dto.getId() != null && !dto.getId().isBlank()
                && dto.getTitle() == null
                && dto.getDescription() == null
                && dto.getVisibility() == null
                && dto.getMembers() != null
                && dto.getMembers().isEmpty();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private boolean isValid(GroupDto dto) {
        if (dto.getMembers() == null) {
            return false;
        }
        final Set<UserId> users = dto.getMembers().stream().flatMap(this::validateUserDto).collect(Collectors.toSet());
        return dto.getId() != null && !dto.getId().isBlank()
                && dto.getTitle() != null
                && !dto.getTitle().isBlank()
                && dto.getDescription() != null
                && ("PRIVATE".equals(dto.getVisibility()) || "PUBLIC".equals(dto.getVisibility()))
                && users.size() == dto.getMembers().size();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Stream<UserId> validateUserDto(UserDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            return Stream.empty();
        }
        return Stream.of(new UserId(dto.getId()));
    }

    private abstract static class ValidatedGroup extends Group {

        /* default */ ValidatedGroup(GroupMetaInf metaInf, Set<UserId> users) {
            super(metaInf, users);
        }

        public abstract boolean isValid();
        public abstract boolean isDeleted();
    }

    private static class ValidGroup extends ValidatedGroup {

        /* default */ ValidGroup(GroupMetaInf metaInf, Set<UserId> users) {
            super(metaInf, users);
        }

        @Override
        public boolean isDeleted() {
            return false;
        }

        @Override
        public boolean isValid() {
            return true;
        }
    }

    private static class DeletedGroup extends ValidatedGroup {

        /* default */ DeletedGroup(GroupId groupId) {
            super(new GroupMetaInf(groupId, null, null), null);
        }

        @Override
        public boolean isDeleted() {
            return true;
        }

        @Override
        public boolean isValid() {
            return false;
        }
    }
}
