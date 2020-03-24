package mops.infrastructure.adapters.webflow.datepoll.webflowdtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mops.infrastructure.adapters.webflow.dtos.GeneralDto;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class MetaInfDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 452345657L;

    private String title;
    private String description;
    private String location;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
}
