package mops.domain.models;

import mops.controllers.dto.InputFieldNames;

import java.util.EnumMap;

public class Validation {
    private EnumMap<InputFieldNames, String> errorMessages;

    private Validation() {
        this.errorMessages = new EnumMap<>(InputFieldNames.class);
    }

    public static Validation noErrors() {
        return new Validation();
    }

    public Validation(InputFieldNames wrongInputfields, String message) {
        initEnumIfNotPresent();
        errorMessages.put(wrongInputfields, message);
    }

    /**
     * ...
     * @return ...
     */
    public boolean hasNoErrors() {
        return errorMessages.isEmpty();
    }

    /**
     * ...
     * @param validation ...
     * @return ...
     */
    public Validation appendValidation(Validation validation) {
        initEnumIfNotPresent();
        errorMessages.putAll(validation.errorMessages);
        return this;
    }

    private void initEnumIfNotPresent() {
        if (errorMessages == null) {
            this.errorMessages = new EnumMap<>(InputFieldNames.class);
        }
    }
}
