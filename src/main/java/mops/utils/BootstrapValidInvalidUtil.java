package mops.utils;

public final class BootstrapValidInvalidUtil {

    private BootstrapValidInvalidUtil() {
    }

    /**
     * Die Methode weist einem HTML-Input-Tag (k)eine Bootstrap-Klasse zu, falls das Eingabefeld gültig oder ungültig
     * ist oder noch gar nichts eingegeben wurde.
     *
     * Verwendung in Thymeleaf:
     * th:classappend="${T(mops.helpers.BootstrapValidInvalid)
     *                   .getClass(#fields.hasErrors('title'), #fields.hasAnyErrors())}"
     *
     * @param fieldHasErrors muss im Template mit der Methode #fields.hasErrors('<fieldname>') ermittelt werden
     * @param formHasErrors muss im Template mit der Methode #fields.hasAnyErrors() ermittelt werden
     * @return gibt entweder „is-invalid“, „is-valid“ oder einen leeren String zurück, sodass das entsprechende
     *         Feld farblich passend markiert wird
     */

    public static String getClass(boolean fieldHasErrors, boolean formHasErrors) {
        // Feld ist ungültig, dann ist es einfach
        if (fieldHasErrors) {
            return "is-invalid";
        } else if (formHasErrors) {
            // Falls andere Felder Fehler enthalten, muss das Formular geprüft worden sein
            // Ergo wird das Feld grün markiert.
            return "is-valid";
        }
        // Ansonsten wurde das Formular noch nicht geprüft, dann soll das Feld auch nicht markiert werden.
        return "";
    }
}
