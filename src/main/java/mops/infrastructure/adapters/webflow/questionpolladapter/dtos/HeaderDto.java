package mops.infrastructure.adapters.webflow.questionpolladapter.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeaderDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 1L;

    private String title;
    private String question;
    private String description;
}
