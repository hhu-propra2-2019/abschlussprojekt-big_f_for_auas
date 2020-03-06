package mops.domain.models;

public class QuestionPollHeader {
  private final String title;
  private final String description;

  public QuestionPollHeader(final String title, final String description) {
    this.title = title;
    if (description == null) {
      this.description = "";
    } else {
      this.description = description;
    }
  }
}
