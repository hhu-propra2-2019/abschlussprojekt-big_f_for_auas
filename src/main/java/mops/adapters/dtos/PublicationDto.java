package mops.adapters.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublicationDto implements Serializable {

    public static final long serialVersionUID = 1239847901845L;

    private boolean ispublic;
    private String link;
    private String people;
    private String groups;
}
