package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.datepoll.PriorityChoiceDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityChoiceJpaRepository extends JpaRepository<PriorityChoiceDao, Long> {
}
