package mops.infrastructure.adapters.webflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class PublicationDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 1239847901845L;

    private boolean ispublic;
    private String link;
    private String people;
    private String groups;
}
