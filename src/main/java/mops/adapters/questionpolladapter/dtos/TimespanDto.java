package mops.adapters.questionpolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class TimespanDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 1L;

    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
}
