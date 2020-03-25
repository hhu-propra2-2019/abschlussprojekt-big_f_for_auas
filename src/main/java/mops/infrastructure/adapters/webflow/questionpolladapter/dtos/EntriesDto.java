package mops.infrastructure.adapters.webflow.questionpolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mops.infrastructure.adapters.webflow.dtos.GeneralDto;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntriesDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 1L;

    //private String question;
    private Set<EntryDto> entries = new TreeSet<>();
}
