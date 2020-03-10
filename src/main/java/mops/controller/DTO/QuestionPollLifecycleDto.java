package mops.controller.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPollLifecycleDto {
    private Date start;
    private Date end;
}
