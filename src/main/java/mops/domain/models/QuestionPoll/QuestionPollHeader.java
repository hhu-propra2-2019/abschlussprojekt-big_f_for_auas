package mops.domain.models.QuestionPoll;

public class QuestionPollHeader {
  private final String title;
  private final String question;
  private final String description;

  public QuestionPollHeader(final String title, final String question, final String description) {
    this.title = title;
    this.question = question;
    if (description == null) {
      this.description = "";
    } else {
      this.description = description;
    }
  }
}
