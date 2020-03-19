package mops.infrastructure.database.daos.questionpoll;

import lombok.Data;
import mops.infrastructure.database.daos.PollLifeCycleDao;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
public class QuestionPollMetaInfDao {
    private String title;
    private String description;
    private String question;
    @Embedded
    private PollLifeCycleDao pollLifeCycle;
}
