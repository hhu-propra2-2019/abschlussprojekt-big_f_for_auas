package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import org.springframework.binding.message.MessageResolver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Speichert Einstellungen ob die QuestionPoll anonym ist (usingAlias = true) und ob es sich um eine
 *  Multi-Choice Umfrage geben.
 */
@Value
@With
@AllArgsConstructor
public class QuestionPollConfig implements Serializable {

    private boolean usingAlias;
    private boolean usingMultiChoice;

    public QuestionPollConfig() {
        this.usingAlias = false;
        this.usingMultiChoice = false;
    }

    /**
     * ...
     * @return ...
     */
    public List<MessageResolver> validate() {
        final List<MessageResolver> messageList = new ArrayList<>();
        validateUsingAlias().ifPresent(messageList::add);
        validateUsingMultiChoice().ifPresent(messageList::add);
        return messageList;
    }

    private Optional<MessageResolver> validateUsingAlias() {
        return Optional.empty();
    }

    private Optional<MessageResolver> validateUsingMultiChoice() {
        return Optional.empty();
    }
}
