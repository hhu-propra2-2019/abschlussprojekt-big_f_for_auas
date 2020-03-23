package mops.infrastructure.database.daos.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.infrastructure.database.daos.TimespanDao;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPollMetaInfDao {
    private String title;
    private String question;
    private String description;
    @Embedded
    private TimespanDao timespan;
}
