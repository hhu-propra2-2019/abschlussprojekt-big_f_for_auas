package mops.domain.models.QuestionPoll;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import mops.controller.DTO.QuestionPollAccessibilityDto;
import mops.controller.DTO.QuestionPollBallotDto;
import mops.controller.DTO.QuestionPollConfigDto;
import mops.controller.DTO.QuestionPollDtoCookie;
import mops.controller.DTO.QuestionPollEntryDto;
import mops.controller.DTO.QuestionPollHeaderDto;
import mops.domain.models.User.UserId;

public class QuestionPollFactory {

  private final static String NOT_SET = "Wert noch nicht gesetzt";
  private final static String INVALID_VALUE = "Ungülitger Wert";

  private QuestionPollAccessibility accessibilityTarget;
  private QuestionPollBallot ballotTarget;
  private QuestionPollConfig configTarget;
  private List<QuestionPollEntry> entryTarget;
  private QuestionPollHeader headerTarget;
  private UserId ownerTarget;

  /** Die Factory verwendet ein cookie System um zu überprüfen ob er in ein valides QuestionPoll Objekt bauen kann.
   *  Für jede Entity/Value in QuestionPoll wird ein cookie in das jar gelegt.
   *  Wird über den builder ein valides DTO übergeben und ein korrekte Entity/Value erzeugt, wird ein Cookie aus dem jar entfernt
   *  Wird ein invalides DTO übergeben muss ein entsprechender Cookie in das Jar hinterlegt werden.
   *  Ein QuestionPoll Objekt kann erst erzeugt werden wenn alle cookies entfernt wurden.
   */
  private EnumMap<QuestionPollDtoCookie,String> cookieJar;

  public QuestionPollFactory(UserId userId) {
    this.accessibilityTarget = null;
    this.ballotTarget = null;
    this.configTarget = null;
    this.entryTarget = null;
    this.headerTarget = null;
    this.ownerTarget = userId;

    this.cookieJar = new EnumMap<>(QuestionPollDtoCookie.class);
    EnumSet.allOf(QuestionPollDtoCookie.class)
        .forEach(key -> cookieJar.put(key, NOT_SET));
  }

  public void accessibility(QuestionPollAccessibilityDto accessibilityDto) {
    try {
      this.accessibilityTarget = new QuestionPollAccessibility(accessibilityDto.restrictedAccess, accessibilityDto.getParticipants());
      this.cookieJar.remove(QuestionPollDtoCookie.ACCESSIBILITY);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.ACCESSIBILITY, INVALID_VALUE);
    }
  }

  public void ballot(QuestionPollBallotDto ballotDto) {
    try {
      this.ballotTarget = new QuestionPollBallot(ballotDto.getVotes());
      this.cookieJar.remove(QuestionPollDtoCookie.BALLOT);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.BALLOT, INVALID_VALUE);
    }
  }

  public void config(QuestionPollConfigDto configDto) {
    try {
      this.configTarget = new QuestionPollConfig(configDto.isUsingAlias(), configDto.isUsingMultiChoice());
      this.cookieJar.remove(QuestionPollDtoCookie.CONFIG);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.CONFIG, INVALID_VALUE);
    }
  }

  public void entries(List<QuestionPollEntryDto> entryDtoList) {
    try {
      List<QuestionPollEntry> questionPollEntryList = entryDtoList.stream()
          .map(entry -> new QuestionPollEntry(entry.getTitle(), entry.getCount()))
          .collect(Collectors.toList());
      this.entryTarget = questionPollEntryList;
      this.cookieJar.remove(QuestionPollDtoCookie.ENTRY);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.ENTRY, INVALID_VALUE);
    }
  }

  public void header(QuestionPollHeaderDto headerDto) {
    try {
      this.headerTarget = new QuestionPollHeader(headerDto.getTitle(),headerDto.getQuestion(), headerDto.getDescription());
      this.cookieJar.remove(QuestionPollDtoCookie.HEADER);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.HEADER, INVALID_VALUE);
    }
  }

  public QuestionPoll build() throws IllegalStateException {
    if (this.cookieJar.isEmpty()) {
      return new QuestionPoll(this.ownerTarget,
          this.headerTarget,
          this.configTarget,
          this.accessibilityTarget,
          this.entryTarget,
          this.ballotTarget);
    } else {
      throw new IllegalStateException("NOT ALL FIELDS SET CORRECTLY");
    }
  }
}
