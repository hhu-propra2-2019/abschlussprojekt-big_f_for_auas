package mops.infrastructure.database.repositories;

import lombok.NoArgsConstructor;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.repositories.DomainGroupRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.GroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DomainGroupRepositoryImpl implements DomainGroupRepository {

    private final transient GroupJpaRepository groupJpaRepository;

    @Autowired
    public DomainGroupRepositoryImpl(GroupJpaRepository groupJpaRepository) {
        this.groupJpaRepository = groupJpaRepository;
    }

    /**
     * ...
     * @param groupId
     * @return
     */
    @Override
    public Optional<Group> load(GroupId groupId) {
        Optional<GroupDao> dao = groupJpaRepository.findById(groupId.getId());
        final Optional<Group> result;
        if (dao.isPresent()) {
            GroupDao present = dao.get();
            result = Optional.of(ModelOfDaoUtil.groupOf(present));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    /**
     * ...
     * @param group
     */
    @Override
    public void save(Group group) {
        GroupDao dao = DaoOfModelUtil.groupDaoOf(group);
        groupJpaRepository.save(dao);
    }
}
