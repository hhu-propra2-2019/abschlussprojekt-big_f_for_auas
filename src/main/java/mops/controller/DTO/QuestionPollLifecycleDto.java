package mops.controller.DTO;

import java.util.Date;
import lombok.Data;

@Data
public class QuestionPollLifecycleDto {
    private Date start;
    private Date end;
}
