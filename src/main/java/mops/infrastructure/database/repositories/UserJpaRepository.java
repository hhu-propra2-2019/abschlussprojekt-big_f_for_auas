package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserJpaRepository extends JpaRepository<UserDao, Long> {
    Set<UserDao> findByDatePollSetContains(Long id);
}
