package mops.infrastructure.database.daos.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
public class QuestionPollConfigDao {
    private boolean singleChoice;
    private boolean anonymous;
    private boolean open;
}
