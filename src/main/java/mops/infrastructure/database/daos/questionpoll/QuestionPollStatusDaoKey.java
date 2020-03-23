package mops.infrastructure.database.daos.questionpoll;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Data
public class QuestionPollStatusDaoKey implements Serializable {
    public static final long serialVersionUID = 203148L;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "questionpoll_link")
    private Long questionPollLink;
}
