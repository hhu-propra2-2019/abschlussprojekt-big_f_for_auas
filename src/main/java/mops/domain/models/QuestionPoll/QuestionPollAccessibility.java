package mops.domain.models.QuestionPoll;

import java.util.Set;
import lombok.Value;
import mops.domain.models.User.UserId;

/**
 * Value Objekt welches die Konfiguration über den Zugriff auf die Abstimmung abkapselt.
 * Ist restrictedAccess = true, handelt es sich um eine Abstimmung in der es nur einer begrenzten Anzahl
 * von Nutzern erlaubt ist abzustimmen. Die Nutzer werden in diesem Fall in der participants Liste gepseichert.
 * Ist restrictedAccess = false, handelt es sich um eine offene Abstimmung und jeder kann abstimmen.
 * In diesem Fall spielt die participants Liste keine weitere Rolle.
 */
@Value
public class QuestionPollAccessibility {

  private final boolean restrictedAccess;
  private final Set<UserId> participants;
}
