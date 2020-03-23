package mops.infrastructure.adapters.webflow.datepoll.builderdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.datepoll.DatePollEntry;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Entries {

    private Set<DatePollEntry> entries = new HashSet<>();
}
