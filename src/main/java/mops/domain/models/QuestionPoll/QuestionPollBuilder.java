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

public class QuestionPollBuilder {

  private final static String NOT_SET = "Wert noch nicht gesetzt";
  private final static String INVALID_VALUE = "Ungülitger Wert";

  private QuestionPollAccessibility accessibilityTarget;
  private QuestionPollBallot ballotTarget;
  private QuestionPollConfig configTarget;
  private List<QuestionPollEntry> entryTarget;
  private QuestionPollHeader headerTarget;
  private UserId ownerTarget;

  private EnumMap<QuestionPollDtoCookie,String> cookieJar;

  public QuestionPollBuilder(UserId userId) {
    this.accessibilityTarget = null;
    this.ballotTarget = null;
    this.configTarget = null;
    this.entryTarget = null;
    this.headerTarget = null;
    this.ownerTarget = userId;

    this.cookieJar = new EnumMap<>(QuestionPollDtoCookie.class);
    EnumSet.allOf(QuestionPollDtoCookie.class)
        .forEach(key -> cookieJar.put(key, NOT_SET));

    return this;
  }

  public QuestionPollBuilder accessibility(QuestionPollAccessibilityDto accessibilityDto) {
    try {
      this.accessibilityTarget = new QuestionPollAccessibility(accessibilityDto.isRestrictedAccesss(),
          accessibilityDto.getParticipants());
      this.cookieJar.remove(QuestionPollDtoCookie.ACCESSIBILITY);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.ACCESSIBILITY, INVALID_VALUE);
    }

    return this;
  }

  public QuestionPollBuilder ballot(QuestionPollBallotDto ballotDto) {
    try {
      this.ballotTarget = new QuestionPollBallot(ballotDto.getVotes());
      this.cookieJar.remove(QuestionPollDtoCookie.BALLOT);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.BALLOT, INVALID_VALUE);
    }

    return this;
  }

  public QuestionPollBuilder config(QuestionPollConfigDto configDto) {
    try {
      this.configTarget = new QuestionPollConfig(configDto.isUsingAlias(), configDto.isUsingMultiChoice());
      this.cookieJar.remove(QuestionPollDtoCookie.CONFIG);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.CONFIG, INVALID_VALUE);
    }

    return this;
  }

  public QuestionPollBuilder entries(List<QuestionPollEntryDto> entryDtoList) {
    try {
      List<QuestionPollEntry> questionPollEntryList = entryDtoList.stream()
          .map(entry -> new QuestionPollEntry(entry.getTitle(), entry.getCount()))
          .collect(Collectors.toList());
      this.entryTarget = questionPollEntryList;
      this.cookieJar.remove(QuestionPollDtoCookie.ENTRY);
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.ENTRY, INVALID_VALUE);
    }

    return this;
  }

  public QuestionPollBuilder header(QuestionPollHeaderDto headerDto) {
    try {
      this.headerTarget = new QuestionPollHeader(headerDto.getTitle() ,headerDto.getDescription());
      this.cookieJar.remove(QuestionPollDtoCookie.)
    } catch (IllegalStateException e) {
      this.cookieJar.put(QuestionPollDtoCookie.HEADER, INVALID_VALUE);
    }

    return this;
  }
}
