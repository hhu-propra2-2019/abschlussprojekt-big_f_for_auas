package mops.domain.models.QuestionPoll;

import mops.domain.models.User.UserId;

import java.util.HashSet;
import java.util.Set;

/**
 * Value Objekt welches die Konfiguration über den Zugriff auf die Abstimmung abkapselt.
 * Ist restrictedAccess = true, handelt es sich um eine Abstimmung in der es nur einer begrenzten Anzahl
 * von Nutzern erlaubt ist abzustimmen. Die Nutzer werden in diesem Fall in der participants Liste gepseichert.
 * Ist restrictedAccess = false, handelt es sich um eine offene Abstimmung und jeder kann abstimmen.
 * In diesem Fall spielt die participants Liste keine weitere Rolle.
 */

public class QuestionPollAccessibility {

  private final boolean restrictedAccess;
  private final Set<UserId> participants;

  public QuestionPollAccessibility(boolean pRestrictedAccess, Set<UserId> pParticipants) {
    this.restrictedAccess = pRestrictedAccess;
    participants = new HashSet<UserId>();
    pParticipants.stream().forEach(id -> participants.add(id));
  }

  public boolean getRestrictedAccess() {
    return restrictedAccess;
  }

  public boolean isUserParticipant(UserId id) {
    if (participants.contains(id)) {
      return true;
    }
    return false;
  }

  public void addUserId(UserId userId) {
    participants.add(userId);
  }
}
