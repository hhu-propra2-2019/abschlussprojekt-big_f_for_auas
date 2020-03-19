package mops.infrastructure.database.daos.questionpoll;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class QuesionPollConfigDao {
    private boolean anonymous;
    private boolean visibility;
    private boolean singlechoice;
}
