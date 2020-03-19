package mops.adapters.datepolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper = true)
@Data
public class OptionsDto extends GeneralDto implements Serializable {

    private String question;
    private Set<OptionDto> options = new TreeSet<>();
    /*= Arrays.asList(
            new OptionDto(LocalDate.now().toString(), "12:00", "13:00"),
            new OptionDto(LocalDate.now().plusDays(1).toString(), "13:00", "14:00")
    );*/

    private String addDate;
    private String addStartTime;
    private String addEndTime;
}
