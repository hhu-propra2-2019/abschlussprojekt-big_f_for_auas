package mops.domain.models.pollstatus;

import lombok.Getter;

public enum PollStatus {
    OPEN("open.svg"),
    REOPENED("reopened.svg"),
    ONGOING("current.svg"),
    TERMINATED("terminated.svg");

    @Getter
    private final String iconName;

    PollStatus(String iconName) {
        this.iconName = iconName;
    }
}
