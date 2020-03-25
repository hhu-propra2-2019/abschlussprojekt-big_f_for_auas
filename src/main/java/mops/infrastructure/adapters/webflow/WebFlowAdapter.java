package mops.infrastructure.adapters.webflow;

import org.springframework.binding.message.MessageContext;

import javax.validation.constraints.NotNull;

/**
 * Ein Adapter verbindet die Templates bzw. den View Layer mit der Domain. Der Adapter ist dabei eine Hilfskomponente
 * für die Services, die mit den jeweiligen Buildern eine DatePoll oder QuestionPoll bauen.
 *
 * @param <D> Das DTO, das für den Datentransfer zwischen Templates und Domain benutzt wird.
 * @param <O> Das Objekt, das der Adapter aus dem DTO baut. Entweder aus der Domain oder ein Hilfsobjekt.
 */
public interface WebFlowAdapter<D, O> {

    /**
     * Hier sollen die DTOs mit sinnvollen Default-Werten befüllt werden.
     * Alle Werte sollten initialisiert werden, sodass man auch bei neu initialisierten Objekt
     * Methoden auf jedes Attribut aufrufen kann.
     *
     * @return ein initialisiertes DTO des entsprechenden Typs
     */
    @NotNull
    D initializeDto();

    /**
     * Diese Methode wird unter anderem bei einem Übergang („transition“) von einer zur anderen Web-Flow-View
     * aufgerufen. Wenn diese Methode true zurückgibt, MUSS beim Aufruf von build() mit demselben Objekt
     * ein gültiges und validiertes Objekt aus der Domain zurückgegeben werden.
     * @param dto Das zu validierende DTO
     * @param context Im MessageContext können Fehlermeldungen angelegt werden. Siehe ErrorMessageHelper
     * @return Der Return-Wert gibt an, ob in die nächste View gewechselt werden soll oder nicht.
     */
    boolean validateDto(D dto, MessageContext context);

    /**
     * In dieser Methode wird das entsprechende Objekt aus der Domain oder das Hilfsobjekt gebaut.
     * Die Methode muss ein gültiges Objekt zurückliefern, wenn validateDto mit dem selben Objekt true geliefert hat.
     * @param dto Das in ein Domain-Objekt zu überführende DTO
     * @return Das Domain-Objekt oder ein Hilfsobjekt („builderdto“), um Domain-Objekte zu bauen
     */
    @NotNull
    O build(D dto);
}
