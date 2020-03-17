package mops.domain.models;

import java.util.EnumSet;

public final class Validation {
    private EnumSet<FieldErrorNames> errorMessages;

    private Validation() {
        this.errorMessages = EnumSet.noneOf(FieldErrorNames.class);
    }

    public static Validation noErrors() {
        return new Validation();
    }

    public Validation(FieldErrorNames wrongInputfields) {
        this();
        errorMessages.add(wrongInputfields);
    }

    public EnumSet<FieldErrorNames> getErrorMessages() {
        return EnumSet.copyOf(errorMessages);
    }

    public boolean hasNoErrors() {
        return errorMessages.isEmpty();
    }

    public Validation appendValidation(Validation validation) {
        Validation newValidation = noErrors();
        newValidation.errorMessages = EnumSet.copyOf(errorMessages);
        newValidation.errorMessages.addAll(validation.errorMessages);
        return newValidation;
    }

    public Validation removeErrors(PollFields fieldTyp) {
        Validation newValidation = noErrors();
        newValidation.errorMessages = EnumSet.copyOf(errorMessages);
        newValidation.errorMessages
                .stream()
                .filter(error -> error.isChildOf(fieldTyp))
                .forEach(errorMessages::remove);
        return newValidation;
    }
}
