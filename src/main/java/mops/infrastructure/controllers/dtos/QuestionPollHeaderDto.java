package mops.infrastructure.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPollHeaderDto {

    private String title;
    private String question;
    private String description;

}
