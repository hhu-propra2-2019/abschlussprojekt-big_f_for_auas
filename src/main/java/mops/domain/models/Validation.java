package mops.domain.models;

import mops.controllers.InputFieldNames;

import java.util.EnumMap;

public class Validation {
    private EnumMap<InputFieldNames, String> errorMessages;

    public static Validation noErrors() {
        return new Validation();
    }

    public boolean hasNoErrors() {
        return true;
    }

    public Validation appendValidation(Validation validation) {
        return this;
    }

    private Validation() {
        errorMessages = new EnumMap<>(InputFieldNames.class);
    }

    public Validation(InputFieldNames wrongInputfields, String message) {
        errorMessages.put(wrongInputfields, message);
    }
}
