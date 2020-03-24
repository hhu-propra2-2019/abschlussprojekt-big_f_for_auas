package mops.infrastructure.adapters.webflow.questionpolladapter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public final class EntryDto implements Comparable<EntryDto>, Serializable {

    public static final long serialVersionUID = 1L;

    private String entry;

    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public int compareTo(EntryDto entryDto) {
        return this.entry.compareTo(entryDto.entry);
    }
}
