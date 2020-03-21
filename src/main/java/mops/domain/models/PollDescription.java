package mops.domain.models;


import lombok.Value;
import mops.utils.DomainObjectCreationUtils;

@Value
public final class PollDescription implements ValidateAble {

    public static final int MAX_DESCRIPTION_LENGTH = 600;
    private String descriptionText;

    public PollDescription(String descriptionText) {
        this.descriptionText = DomainObjectCreationUtils.convertNullToEmptyAndTrim(descriptionText);
    }

    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (descriptionText.length() > MAX_DESCRIPTION_LENGTH) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_DESCRIPTION_TOO_LONG));
        }
        return validation;
    }
}
