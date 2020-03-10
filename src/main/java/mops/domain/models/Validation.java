package mops.domain.models;

import mops.controllers.InputFieldNames;

import java.util.EnumMap;

public class Validation {
    private EnumMap<InputFieldNames, String> errorMessages;

    public boolean hasNoErrors() {
        return true;
    }

    public Validation appendValidation(Validation validation) {
        return this;
    }
}
