package mops.infrastructure.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatePollConfigDto {

    private boolean anonymous;
    private boolean singleChoice;
    private boolean priorityChoice;
}
