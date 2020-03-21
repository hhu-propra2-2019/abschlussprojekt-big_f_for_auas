package mops.adapters.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublicationDto implements Serializable {

    private boolean ispublic;
    private String link;
    private String people;
    private String groups;
}
