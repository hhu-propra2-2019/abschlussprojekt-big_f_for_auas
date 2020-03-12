package mops.domain.models;

public interface ValidateAble {

    /**
     * Validiert den internen Status der Klasse.
     * @return Validation objekt, dass die Validierung kapselt.
     */
    Validation validate();

    default boolean hasNoErrors() {
        return validate().hasNoErrors();
    }
}
