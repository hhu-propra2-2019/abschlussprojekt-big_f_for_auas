package mops.infrastructure.database.daos.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPollConfigDao {
    private boolean singleChoice;
    private boolean anonymous;
    private boolean open;
}
