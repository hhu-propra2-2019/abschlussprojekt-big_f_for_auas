package mops.infrastructure.adapters.webflow.datepoll.webflowdtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mops.infrastructure.adapters.webflow.dtos.GeneralDto;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 43545553524L;

    private boolean voteIsEditable;
    private boolean openForOwnEntries;
    private boolean singleChoice;
    private boolean priorityChoice;
    private boolean anonymous;

    // Nur für das Erzeugen des DatePollMetaInf, wird nicht gemappt. Der relevante Wert ist in PublicationDto
    // TODO: Bessere Lösung überlegen
    private boolean open;
}
