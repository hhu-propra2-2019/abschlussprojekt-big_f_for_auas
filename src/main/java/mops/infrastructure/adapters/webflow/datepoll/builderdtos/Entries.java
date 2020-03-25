package mops.infrastructure.adapters.webflow.datepoll.builderdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.datepoll.DatePollEntry;

import java.util.Set;

@Data
@AllArgsConstructor
@SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
public class Entries {

    private Set<DatePollEntry> entries;
}
