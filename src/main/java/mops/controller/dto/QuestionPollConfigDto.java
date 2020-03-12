package mops.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPollConfigDto {

    private boolean usingAlias;
    private boolean usingMultiChoice;

}
