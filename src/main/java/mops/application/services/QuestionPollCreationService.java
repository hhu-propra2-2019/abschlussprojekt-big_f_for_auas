package mops.application.services;

import java.util.List;

import lombok.NoArgsConstructor;
import mops.controllers.dtos.QuestionPollConfigDto;
import mops.controllers.dtos.QuestionPollEntryDto;
import mops.controllers.dtos.QuestionPollHeaderDto;
import mops.controllers.dtos.QuestionPollLifecycleDto;
import mops.domain.models.questionpoll.QuestionPollFactory;
import mops.domain.models.repository.UserRepositoryInterface;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor // PMD zuliebe
public final class QuestionPollCreationService {

  private UserRepositoryInterface userRepo;

  /**
   * Erstellt eine Factory für eine QuestionPoll und gibt diese zurück.
   * Der übergebene Nutzer wird automatisch als Ersteller der QuestionPoll gesetzt.
   * @param userId
   * @return QuestionPollFactory
   */
  public QuestionPollFactory startQuestionPoll(final UserId userId) {
    return new QuestionPollFactory(userId);
  }

  /**
   * Setzt den Header (Titel, Frage und optionale Beschreibung) in der Factory.
   * @param factory
   * @param headerDto
   */
  public void addHeader(final QuestionPollFactory factory, final QuestionPollHeaderDto headerDto) {
    factory.header(headerDto);
  }

  /**
   * Setzt die Entries (Elemente über die in der Abstimmung gewählt werden) in der Factory.
   * @param factory
   * @param entryDtoList
   */
  public void addEntries(final QuestionPollFactory factory, final List<QuestionPollEntryDto> entryDtoList) {
    factory.entries(entryDtoList);
  }

  /**
   * Setzt die Konfiguration in der Factory. Die Konfiguration bestimmt ob die Abstimmung anonym ablaufen wird
   * und ob es sich um eine Single-Choice Abstimmung oder eine Multi-Choice Abstimmung handelt.
   * Die Konfiguration ist unveränderlich.
   * @param factory
   * @param configDto
   */
  public void addConfig(final QuestionPollFactory factory, final QuestionPollConfigDto configDto) {
    factory.config(configDto);
  }

  /**
   * Setzt das Lifecycle Objekt in der Factory. Das Lifecycleobjekt bestimmt in welchem Zeitraum abgestimmt werden kann.
   * @param factory
   * @param lifecycleDto
   */
  public void addLifecycle(final QuestionPollFactory factory, final QuestionPollLifecycleDto lifecycleDto) {
    factory.lifecycle(lifecycleDto);
  }

}
