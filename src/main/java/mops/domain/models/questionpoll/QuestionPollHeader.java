package mops.domain.models.questionpoll;

import lombok.NonNull;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

public class QuestionPollHeader implements ValidateAble {

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

  @Override
  public Validation validate() {
    return Validation.noErrors();
  }
}
