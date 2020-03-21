package mops.infrastructure.database.daos.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.infrastructure.database.daos.TimespanDao;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
@AllArgsConstructor
class QuestionPollMetaInfDao {
    private String title;
    private String description;
    private String question;
    @Embedded
    private TimespanDao pollLifeCycle;
}
