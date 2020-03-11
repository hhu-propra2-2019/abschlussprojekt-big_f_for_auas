package mops.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPollEntryDto {

    private String title;
    private long count;

}
