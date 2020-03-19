package mops.domain.models.datepoll;

import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

import java.util.Collections;
import java.util.Set;

@Getter
public class DatePollBallot implements ValidateAble {
    private final UserId user;
    private final Set<DatePollEntry> selectedEntriesYes;
    private final Set<DatePollEntry> selectedEntriesMaybe;

    /**
     * Konstruktor wo man ja und vielleicht abstimmen kann.
     * @param qpUserId
     * @param selectedEntriesYes
     * @param selectedEntriesMaybe
     */
    public DatePollBallot(UserId qpUserId, Set<DatePollEntry> selectedEntriesYes,
        Set<DatePollEntry> selectedEntriesMaybe) {
        this.user = qpUserId;
        this.selectedEntriesYes = Collections.unmodifiableSet(selectedEntriesYes);
        this.selectedEntriesMaybe = Collections.unmodifiableSet(selectedEntriesMaybe);
    }

    /** Konstruktor wo man ja und vielleicht abstimmen kann.
     *
     * @param qpUserId
     * @param selectedEntriesYes
     */
    public DatePollBallot(UserId qpUserId, Set<DatePollEntry> selectedEntriesYes) {
        this.user = qpUserId;
        this.selectedEntriesYes = Collections.unmodifiableSet(selectedEntriesYes);
        this.selectedEntriesMaybe = Collections.unmodifiableSet(Collections.emptySet());
    }

    /**
     * Anzahl der ja stimmen.
     * @return int
     */
    public int getYesEntriesSize() {
        return selectedEntriesYes.size();
    }

    /**
     * Anzahl der vielleicht stimmen.
     * @return int
     */
    public int getMaybeEntriesSize() {
        return selectedEntriesMaybe.size();
    }

    /**
     * ...
     * @return...
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }


}
