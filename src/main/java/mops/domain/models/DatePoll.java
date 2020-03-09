package mops.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DatePoll {
    /**
     * Meta-Informationen zur DatePoll.
     */
    private DatePollMetaInf datePollMetaInf;
    /**
     * Id der DatePoll.
     */
    private DatePollID datePollID;
    /**
     * Creator (Typ User) der DatePoll.
     */
    private User creator;
    /**
     * Konfiguration der DatePoll. Aggregiert in einem eigens dafür angelegten Objekt.
     */
    private DatePollConfig datePollConfig;
    /**
     * Liste der Terminoptionen der DatePoll.
     */
    private List<DatePollOption> datePollOptionList;
    /**
     * Liste der User, die an der Abstimmung teilnehmen **können**.
     */
    private List<User> participants;

    /**
     * Methode zum setzen des PublicationTypes (in DatePollConfig). Der Default ist false, also not Public.
     * @param isPublic boolean der sagt, ob public oder nicht.
     */
    public void setPublicationTypeToPublic(final boolean isPublic) {
        //
    }

    private void addParticipant(final User nextParticipant) {
        this.participants.add(nextParticipant);
    }

    /**
     * Methode, die eine Liste von Usern zu den Participants hinzufügt.
     * @param users Liste der betreffenden User
     */
    public void addListOfUsersToParticipants(final List<User> users) {
        for (User user: users
             ) {
            addParticipant(user);
        }
    }
}
