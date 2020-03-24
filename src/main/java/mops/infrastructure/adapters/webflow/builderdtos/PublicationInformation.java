package mops.infrastructure.adapters.webflow.builderdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.PollLink;
import mops.domain.models.group.GroupId;

import java.util.Set;

@Data
@AllArgsConstructor
public class PublicationInformation {

    private boolean ispublic;
    private PollLink link;
    private Set<GroupId> groups;
}
