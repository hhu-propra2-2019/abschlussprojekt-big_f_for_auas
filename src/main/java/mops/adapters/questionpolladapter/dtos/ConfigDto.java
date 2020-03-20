package mops.adapters.questionpolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 1L;

    private boolean singleChoice;
    private boolean anonymous;
}
