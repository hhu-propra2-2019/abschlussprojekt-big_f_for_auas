package mops.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Getter
    private final UserId id;

    /*
    Set<UserPollStatus> userPollStatus = new HashSet<>()

    @AllArgsConstructor
    public class UserPollStatus {
        Poll poll;
        LocalDateTime lastModified;
        PollStatus status; //das hier wird im Dashboard angezeigt

        //Aufgerufen wenn Poll-Status sich ändert, und wenn User abstimmt
        public PollStatus refreshStatus(Poll poll, LocalDateTime lastModified){
            if (poll.isTerminated()) {
                return TERMINATED;
            }

            if (lastModified == null) {
                return OPEN;
            }

            //poll wurde nachträglich bearbeitet
            if (lastModified < Poll.lastModified()) {
                return REOPENED;
            }

            return ONGOING;
        }

    }
    */

}
