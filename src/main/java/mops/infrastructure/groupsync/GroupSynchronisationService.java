package mops.infrastructure.groupsync;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public final class GroupSynchronisationService {

    private final transient GroupSyncWebclient syncController;

    // In Millisekunden, 1h => 3600s => 3600s * 1000ms
    private static final long SYNCHRONISATION_DELAY = 1000 * 900;
    private static final long INITIAL_SYNCHRONISATION_DELAY = 2000;

    public GroupSynchronisationService(GroupSyncWebclient syncController) {
        this.syncController = syncController;
    }

    @Scheduled(fixedDelay = SYNCHRONISATION_DELAY, initialDelay = INITIAL_SYNCHRONISATION_DELAY)
    public void syncGroups() {
        syncController.getGroupSyncDto();
    }
}
