package mops.infrastructure.adapters.webflow.builderdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.group.GroupId;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class PublicationSettings {

    private boolean ispublic;
    private DatePollLink link;
    private Set<GroupId> groups;
}
