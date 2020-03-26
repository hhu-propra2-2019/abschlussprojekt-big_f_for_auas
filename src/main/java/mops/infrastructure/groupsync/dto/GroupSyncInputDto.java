package mops.infrastructure.groupsync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class GroupSyncInputDto {

    @JsonProperty(required = true)
    private Long status;
    @JsonProperty(required = true)
    private Set<GroupDto> groupList;

}
