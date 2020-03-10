package mops.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPollConfigDto {

    private boolean usingAlias;
    private boolean usingMultiChoice;

}
