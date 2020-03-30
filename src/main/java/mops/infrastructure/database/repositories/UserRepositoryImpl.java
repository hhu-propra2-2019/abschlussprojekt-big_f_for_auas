package mops.infrastructure.database.repositories;

import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.domain.repositories.UserRepository;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("checkstyle:DesignForExtension")
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final transient UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> load(UserId userId) {
        final UserDao targetUserDao = userJpaRepository.getOne(userId.getId());
        return Optional.of(ModelOfDaoUtil.userOf(targetUserDao));
    }

    Optional<UserDao> loadDao(UserId userId) { //NOPMD
        if (userJpaRepository.existsById(userId.getId())) {
            return Optional.of(userJpaRepository.getOne(userId.getId()));
        } else {
            return Optional.empty();
        }
    }

    public boolean existsById(UserId userId) {
        return userJpaRepository.existsById(userId.getId());
    }

    @SuppressWarnings("PMD.DefaultPackage")
    /* default */ void updateUser(UserDao userDao) {
        if (userJpaRepository.existsById(userDao.getId())) {
            userJpaRepository.save(userDao);
        }
    }

    @Override
    public void saveUserIfNotPresent(User user) {
        if (!this.existsById(user.getId())) {
            userJpaRepository.save(DaoOfModelUtil.userDaoOf(user));
        }
    }

    public Long getAllYesVotesForDatePollEntry(DatePollEntryDao datePollEntryDao) {
        return userJpaRepository.countByDatePollEntrySetContaining(datePollEntryDao);
    }
}
