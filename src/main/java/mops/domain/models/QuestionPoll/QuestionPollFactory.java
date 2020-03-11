package mops.domain.models.QuestionPoll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import mops.controller.DTO.QuestionPollAccessibilityDto;
import mops.controller.DTO.QuestionPollConfigDto;
import mops.controller.DTO.QuestionPollDtoCookie;
import mops.controller.DTO.QuestionPollEntryDto;
import mops.controller.DTO.QuestionPollHeaderDto;
import mops.controller.DTO.QuestionPollLifecycleDto;
import mops.domain.models.User.UserId;

/**
 * Factory für die Erstellung von eines QuestionPoll Objektes.
 */
public class QuestionPollFactory {

  private static final String NOT_SET = "Wert noch nicht gesetzt";
  private static final String INVALID_VALUE = "Ungülitger Wert";

  private QuestionPollAccessibility accessibilityTarget;
  private QuestionPollBallot ballotTarget;
  private QuestionPollConfig configTarget;
  private List<QuestionPollEntry> entryTarget;
  private QuestionPollHeader headerTarget;
  private UserId ownerTarget;
  private QuestionPollLifecycle lifecycleTarget;

  /** Die Factory verwendet ein cookie System um zu überprüfen ob er in ein valides QuestionPoll Objekt bauen kann.
   *  Für jede Entity/Value in QuestionPoll wird ein cookie in das jar gelegt.
   *  Wird über den builder ein valides DTO übergeben und ein korrekte Entity/Value erzeugt,
   *  wird ein Cookie aus dem jar entfernt
   *  Wird ein invalides DTO übergeben muss ein entsprechender Cookie in das Jar hinterlegt werden.
   *  Ein QuestionPoll Objekt kann erst erzeugt werden wenn alle cookies entfernt wurden.
   */
  private EnumMap<QuestionPollDtoCookie, String> cookieJar;

  public QuestionPollFactory(UserId userId) {
    this.accessibilityTarget = null;
    this.configTarget = null;
    this.entryTarget = null;
    this.headerTarget = null;
    this.ballotTarget = new QuestionPollBallot();
    this.ownerTarget = userId;

    this.cookieJar = new EnumMap<>(QuestionPollDtoCookie.class);
    EnumSet.allOf(QuestionPollDtoCookie.class)
        .forEach(key -> cookieJar.put(key, NOT_SET));
  }

  /**
   * Setzt das Accessibility Objekt im Factory.
   * @param accessibilityDto
   */
  public void accessibility(QuestionPollAccessibilityDto accessibilityDto) {
    try {
      this.accessibilityTarget = new QuestionPollAccessibility(accessibilityDto.isRestrictedAccess(),
          accessibilityDto.getParticipants());
      this.cookieJar.remove(QuestionPollDtoCookie.ACCESSIBILITY);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.ACCESSIBILITY, INVALID_VALUE);
    }
  }

  /**
   * Fügt der gesetzten Participant Liste einen Nutzer hinzu.
   * @param userId
   */
  public void accessibilityAddUser(UserId... userId) {
    if (!this.cookieJar.containsKey(QuestionPollDtoCookie.ACCESSIBILITY)) {
      Arrays.stream(userId).forEach(thisUserId -> this.accessibilityTarget.getParticipants().add(thisUserId));
    }
  }

  /**
   * Setzt die Config in der Factory.
   * @param configDto
   */
  public void config(QuestionPollConfigDto configDto) {
    try {
      this.configTarget = new QuestionPollConfig(configDto.isUsingAlias(), configDto.isUsingMultiChoice());
      this.cookieJar.remove(QuestionPollDtoCookie.CONFIG);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.CONFIG, INVALID_VALUE);
    }
  }

  /**
   * Setzt die Liste der Entries in der Factory.
   * @param entryDtoList
   */
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

  /**
   * Setzt den Header in der Factory.
   * @param headerDto
   */
  public void header(QuestionPollHeaderDto headerDto) {
    try {
      this.headerTarget = new QuestionPollHeader(headerDto.getTitle(),
          headerDto.getQuestion(), headerDto.getDescription());
      this.cookieJar.remove(QuestionPollDtoCookie.HEADER);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.HEADER, INVALID_VALUE);
    }
  }

  /**
   * Setzt den Lifecycle in der Factory.
   * @param lifecycleDto
   */
  public void lifecycle(QuestionPollLifecycleDto lifecycleDto) {
    try {
      this.lifecycleTarget = new QuestionPollLifecycle(lifecycleDto.getStart(), lifecycleDto.getEnd());
      this.cookieJar.remove(QuestionPollDtoCookie.LIFECYCLE);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.LIFECYCLE, INVALID_VALUE);
    }
  }

  /**
   * Erzeugt aus den gesetzten Feldern ein QuestionPoll Objekt.
   * Funktioniert nur wenn alle Felder korrekt gesetzt wurde.
   * @return QuestionPoll
   * @throws IllegalStateException
   */
  public QuestionPoll build() throws IllegalStateException {
    if (this.cookieJar.isEmpty()) {
      return new QuestionPoll(this.ownerTarget,
          this.headerTarget,
          this.configTarget,
          this.accessibilityTarget,
          this.entryTarget,
          this.ballotTarget,
          this.lifecycleTarget);
    } else {
      throw new IllegalStateException("NOT ALL FIELDS SET CORRECTLY");
    }
  }

  /**
   * Überprüft den momentanen Zustand der Factory und gibt zurück welche Felder noch nicht korrekt
   * gesetzt oder noch gar nicht gesetzt wurden.
   * @return List<String>
   */
  public List<String> peekCookieJar() {
    List<String> cookies = new ArrayList<String>();
    this.cookieJar.entrySet().forEach(entry -> cookies.add(entry.getKey().toString() + " :: " + entry.getValue()));
    return cookies;
  }
}
