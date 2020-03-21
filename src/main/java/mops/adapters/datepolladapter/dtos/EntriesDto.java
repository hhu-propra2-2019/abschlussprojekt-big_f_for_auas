package mops.adapters.datepolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntriesDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 123423432424L;

    //private String question;
    private Set<EntryDto> options = new TreeSet<>();

}
