package mops.infrastructure.database.repositories.interfaces;

import mops.infrastructure.database.daos.GroupDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupJpaRepository extends JpaRepository<GroupDao, String> {
}
