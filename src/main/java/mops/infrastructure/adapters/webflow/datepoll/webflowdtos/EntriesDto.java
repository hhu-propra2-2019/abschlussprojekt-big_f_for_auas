package mops.infrastructure.adapters.webflow.datepoll.webflowdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mops.infrastructure.adapters.webflow.dtos.GeneralDto;

import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class EntriesDto extends GeneralDto implements Serializable {

    public static final long serialVersionUID = 123423432424L;

    // Der Eintrag mit der aktuellen Uhrzeit, der den User*innen vorgeschlagen wird.
    // So können die Voreinstellungen im EntryAdapter eingestellt werden.
    private EntryDto proposedEntry;

    // Wird mit TreeSet initialisiert, damit die Einträge geordnet sind
    private Set<EntryDto> entries;

}
