package mops.domain.models;

import lombok.Value;
import mops.utils.DomainObjectCreationUtils;

@Value
public class PollLink implements ValidateAble {

    private static final String HOSTNAME = "mops.cs.hhu.de/";

    private String pollIdentifier;

    public PollLink(final String newPollIdentifier) {
        this.pollIdentifier = DomainObjectCreationUtils.convertNullToEmptyAndTrim(newPollIdentifier);
    }

    /**
     * ...
     * @return ...
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if ("".equals(pollIdentifier)) {
            validation = new Validation(FieldErrorNames.DATE_POLL_IDENTIFIER_EMPTY);
        }
        return validation;
    }
}
