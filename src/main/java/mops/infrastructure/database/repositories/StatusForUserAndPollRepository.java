package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.datepoll.DatePollStatusDao;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.DatePollStatusJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatusForUserAndPollRepository {
    private final transient DatePollStatusJpaRepository datePollStatusJpaRepository;
    @Autowired
    public StatusForUserAndPollRepository(DatePollStatusJpaRepository datePollStatusJpaRepository) {
        this.datePollStatusJpaRepository = datePollStatusJpaRepository;
    }

    /**
     * Gibt PollStatus des Users zurück, der zum DatePoll gehört.
     * @param userId
     * @param link
     * @return PollStatus
     */
    public PollStatus getCurrentPollStatusForUser(UserId userId, PollLink link) {
        final DatePollStatusDao datePollStatusDao = datePollStatusJpaRepository.findByParticipantAndDatePoll(
                userId.getId(), link.getLinkUUIDAsString()
        );
        return ModelOfDaoUtil.createPollStatus(datePollStatusDao.getUserStatusForDatePoll());
    }
}
