package mops.adapters.datepolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
