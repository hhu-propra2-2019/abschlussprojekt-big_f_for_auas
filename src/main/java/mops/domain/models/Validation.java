package mops.domain.models;

import mops.controllers.InputFieldNames;

import java.util.EnumMap;

public class Validation {
    private EnumMap<InputFieldNames, String> errorMessages;

    public static Validation noErrors() {
        return new Validation();
    }


    /**
     * Prüft ob Errors in dieser Validierung auffgetreten ist.
     * @return true wenn keine Fehler aufgetreten sind.
     */
    public boolean hasNoErrors() {
        return true;
    }

    /**
     * Verbindet zwei Validerungen zu
     * @param validation
     * @return
     */
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
