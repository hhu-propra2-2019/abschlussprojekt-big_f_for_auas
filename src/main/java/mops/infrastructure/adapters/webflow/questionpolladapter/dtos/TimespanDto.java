package mops.infrastructure.adapters.webflow.questionpolladapter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mops.infrastructure.adapters.webflow.dtos.GeneralDto;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class TimespanDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 1L;

    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
}
