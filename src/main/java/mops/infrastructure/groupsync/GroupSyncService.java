package mops.infrastructure.groupsync;

import mops.domain.repositories.DomainGroupRepository;
import mops.infrastructure.groupsync.dto.GroupSyncInputDto;
import mops.infrastructure.groupsync.dto.GroupSyncValidDto;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;

public final class GroupSyncService {

    private final transient GroupSyncWebclient syncController;
    private final transient GroupSyncValidator syncValidator;
    private final transient DomainGroupRepository groupRepository;

    // In Millisekunden, 1h => 3600s => 3600s * 1000ms
    private static final long SYNCHRONISATION_DELAY = 1000 * 900;
    private static final long INITIAL_SYNCHRONISATION_DELAY = 2000;

    // Der Status wird von der API zurückgegeben, um inkrementelle Updates zu ermöglichen
    // Da er lokal gespeichert wird, wird bei jedem Neustart der Anwendung ein voller Resync getriggert,
    // was auch sinnvoll erscheint, falls durch unvorhersehbare Fehler ein inkonsistenter Zustand entsteht
    private long lastStatus = 0;

    public GroupSyncService(GroupSyncWebclient syncController,
                            GroupSyncValidator syncValidator,
                            DomainGroupRepository groupRepository) {
        this.syncController = syncController;
        this.syncValidator = syncValidator;
        this.groupRepository = groupRepository;
    }

    @Scheduled(fixedDelay = SYNCHRONISATION_DELAY, initialDelay = INITIAL_SYNCHRONISATION_DELAY)
    public void syncGroups() {
        Optional<GroupSyncInputDto> groupSyncInputDto = syncController.getGroupSyncDto(lastStatus);
        if (groupSyncInputDto.isPresent()) {
            GroupSyncValidDto groupSyncValidDto =
                    syncValidator.validateAndKeepValid(groupSyncInputDto.get(), lastStatus);
            groupSyncValidDto.getGroups().forEach(groupRepository::save);
            // Status nur updaten, wenn fehlerfrei eingelesen wurde. Andernfalls zwar die gültigen
            // Gruppen hinzufügen, aber das nächste Mal versuchen, wieder alle Gruppen zu parsen.
            // Ob dieses Verhalten gewünscht ist, kann ich nicht entscheiden.
            // Sorgt aber dafür, dass es zulasten von Overhead im Fall von Fehlern weniger wahrscheinlich ist,
            // dass eine Gruppe vorhanden ist, aber nicht synchronisiert wird.
            if (!groupSyncValidDto.isErrorsOccurred()) {
                lastStatus = groupSyncValidDto.getStatus();
            }
        }
    }
}
