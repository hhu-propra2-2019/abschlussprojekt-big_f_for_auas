package mops.infrastructure.adapters.webflow.datepoll.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mops.infrastructure.adapters.webflow.dtos.GeneralDto;
import mops.infrastructure.adapters.webflow.dtos.PublicationDto;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ConfirmationDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 786187492712436L;

    private MetaInfDto metaInfDto;
    private ConfigDto configDto;
    private EntriesDto entriesDto;
    private PublicationDto publicationDto;
}
