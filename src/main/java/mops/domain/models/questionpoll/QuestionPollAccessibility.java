package mops.domain.models.questionpoll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;
import java.util.Set;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;

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

    public List<MessageResolver> validate(Validation validator) {
        List<MessageResolver> messageList = new ArrayList<>();
        validateParticipants()
            .ifPresent(messageResolver -> messageList.add(messageResolver));
        return messageList;
    }

    private Optional<MessageResolver> validateParticipants() {
        if (this.restrictedAccess && (this.getParticipants().size() < 2)) {
            return Optional.of(
                new MessageBuilder()
                    .error()
                    .source("QuestionPollAccessibility.participants")
                    .defaultText("Eine private Abstimmung muss ziwschen mindestens 2 Person stattfinden")
                    .build());
        }
        return Optional.empty();
    }
}
