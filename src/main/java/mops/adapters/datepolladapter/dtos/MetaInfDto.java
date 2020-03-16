package mops.adapters.datepolladapter.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MetaInfDto implements Serializable {

    private String title = "";
    private String description = "";
    private String location = "";
    private LocalDate startDate = LocalDate.now();
    private LocalTime startTime = LocalTime.now();
    private LocalDate endDate = LocalDate.now();
    private LocalTime endTime = LocalTime.now();
}