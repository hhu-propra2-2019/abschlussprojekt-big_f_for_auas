package mops.domain.models.questionpoll;

import java.util.Collections;
import lombok.Getter;
import mops.domain.models.user.UserId;

import java.util.HashSet;
import java.util.Set;

/**
 * Value Objekt welches die Konfiguration über den Zugriff auf die Abstimmung abkapselt.
 * Ist restrictedAccess = true, handelt es sich um eine Abstimmung in der es nur einer begrenzten Anzahl
 * von Nutzern erlaubt ist abzustimmen. Die Nutzer werden in diesem Fall in der participants Liste gepseichert.
 * Ist restrictedAccess = false, handelt es sich um eine offene Abstimmung und jeder kann abstimmen.
 * In diesem Fall spielt die participants Liste keine weitere Rolle.
 */
@Getter
public class QuestionPollAccessibility {

  private final boolean restrictedAccess;
  private final Set<UserId> participants;

  public QuestionPollAccessibility(boolean pRestrictedAccess, Set<UserId> pParticipants) {
    this.restrictedAccess = pRestrictedAccess;
    this.participants = Collections.unmodifiableSet(pParticipants);
  }


  /**
   * Darf User wählen?
   * @param id
   * @return boolean
   */
  public boolean isUserParticipant(UserId id) {
    return participants.contains(id);
  }

  /**
   * Fügt einen User zu den Participants hinzu.
   * @param userId
   */
  public void addUser(UserId userId) {
    participants.add(userId);
  }
}
