package mops.infrastructure.database.daos.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
class QuesionPollConfigDao {
    private boolean anonymous;
    private boolean visibility;
    private boolean singlechoice;
}
