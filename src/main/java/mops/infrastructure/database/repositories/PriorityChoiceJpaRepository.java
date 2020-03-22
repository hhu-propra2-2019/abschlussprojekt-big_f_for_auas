package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.datepoll.PriorityChoiceDao;
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDaoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityChoiceJpaRepository extends JpaRepository<PriorityChoiceDao, PriorityChoiceDaoKey> {
}
