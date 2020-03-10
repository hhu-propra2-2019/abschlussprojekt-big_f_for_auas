package mops.applicationService;

import java.util.List;
import mops.controller.DTO.QuestionPollBallotDto;
import mops.controller.DTO.QuestionPollConfigDto;
import mops.controller.DTO.QuestionPollEntryDto;
import mops.controller.DTO.QuestionPollHeaderDto;
import mops.domain.models.QuestionPoll.QuestionPollFactory;
import mops.domain.models.Repository.UserRepositoryInterface;
import mops.domain.models.User.User;
import mops.domain.models.User.UserId;


public final class QuestionPollCreationService {

  UserRepositoryInterface userRepo;

  /**
   * Erstellt eine Factory für eine QuestionPoll und gibt diese zurück.
   * @param userId
   * @return QuestionPollFactory
   */
  public QuestionPollFactory startQuestionPoll(final UserId userId) {
    User user = userRepo.getById(userId);
    return new QuestionPollFactory(userId);
  }

  public void addHeader(final QuestionPollFactory factory, final QuestionPollHeaderDto headerDto) {
    factory.header(headerDto);
  }

  public void addEntries(final QuestionPollFactory factory, final List<QuestionPollEntryDto> entryDtoList) {
    factory.entries(entryDtoList);
  }

  public void addConfig(final QuestionPollFactory factory, final QuestionPollConfigDto configDto) {
    factory.config(configDto);
  }

  public void addBallot(final QuestionPollFactory factory, final QuestionPollBallotDto ballotDto) {
    factory.ballot(ballotDto);
  }

}
