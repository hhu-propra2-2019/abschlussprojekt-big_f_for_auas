package mops.domain.models.questionpoll;

import lombok.NonNull;
import lombok.Value;
import mops.domain.models.ValidateAble;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageResolver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Value
public class QuestionPollHeader implements Serializable {

  @NonNull
  private final String title;

  @NonNull
  private final String question;

  private final String description;

  /**
   * Speichert den Titel, die Frage und die optionale Beschreibung für eine QuestionPoll.
   * @param title
   * @param question
   * @param description
   */
  public QuestionPollHeader(final String title, final String question, final String description) {
    this.title = title;
    this.question = question;
    if (description == null) {
      this.description = "";
    } else {
      this.description = description;
    }
  }

  public List<MessageResolver> validate() {
    List<MessageResolver> messageList = new ArrayList<>();
    validateTitle()
            .ifPresent(messageList::add);
    validateDescription()
            .ifPresent(messageList::add);
    validateQuestion()
            .ifPresent(messageList::add);
    return messageList;
  }

  private Optional<MessageResolver> validateTitle() {
    if (title.length() > 4) {
      return Optional.of(
              new MessageBuilder()
                      .error()
                      .source("title")
                      .defaultText("Titel muss kürzer als 5 Zeichen sein !")
                      .build());
    }
    if (title.length() == 0) {
      return Optional.of(
              new MessageBuilder()
                      .error()
                      .source("title")
                      .defaultText("Titel ist ein Pflichtfeld !")
                      .build());
    }
    return Optional.empty();
  }

  private Optional<MessageResolver> validateDescription() {
    if (description.length() > 200) {
      return Optional.of(
              new MessageBuilder()
                      .error()
                      .source("description")
                      .defaultText("Description muss kürzer als 200 Zeichen sein !")
                      .build());
    }
    return Optional.empty();
  }

  private Optional<MessageResolver> validateQuestion() {
    if (question.length() > 30) {
      return Optional.of(
              new MessageBuilder()
                      .error()
                      .source("question")
                      .defaultText("Frage muss kürzer als 30 Zeichen sein !")
                      .build());
    }

    if (question.length() == 0) {
      return Optional.of(
              new MessageBuilder()
                      .error()
                      .source("question")
                      .defaultText("Frage ist ein Pflichtfeld !")
                      .build());
    }
    return Optional.empty();
  }
}
