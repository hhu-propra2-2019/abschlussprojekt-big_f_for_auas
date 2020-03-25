package mops.infrastructure.groupsync;

import mops.infrastructure.groupsync.dto.GroupSyncInputDto;
import mops.infrastructure.groupsync.dto.GroupSyncValidDto;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;

public final class GroupSyncService {

    private final transient GroupSyncWebclient syncController;
    private final transient GroupSyncValidator syncValidator;

    // In Millisekunden, 1h => 3600s => 3600s * 1000ms
    private static final long SYNCHRONISATION_DELAY = 1000 * 900;
    private static final long INITIAL_SYNCHRONISATION_DELAY = 2000;

    // Der Status wird von der API zurückgegeben, um inkrementelle Updates zu ermöglichen
    // Da er lokal gespeichert wird, wird bei jedem Neustart der Anwendung ein voller Resync getriggert,
    // was auch sinnvoll erscheint, falls durch unvorhersehbare Fehler ein inkonsistenter Zustand entsteht
    private long lastStatus = 0;

    public GroupSyncService(GroupSyncWebclient syncController,
                            GroupSyncValidator syncValidator) {
        this.syncController = syncController;
        this.syncValidator = syncValidator;
    }

    @Scheduled(fixedDelay = SYNCHRONISATION_DELAY, initialDelay = INITIAL_SYNCHRONISATION_DELAY)
    public void syncGroups() {
        Optional<GroupSyncInputDto> groupSyncInputDto = syncController.getGroupSyncDto(lastStatus);
        if (groupSyncInputDto.isPresent()) {
            GroupSyncValidDto groupSyncValidDto =
                    syncValidator.validateAndKeepValid(groupSyncInputDto.get(), lastStatus);
        }
    }
}
