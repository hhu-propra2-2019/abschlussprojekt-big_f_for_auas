package mops.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@Value
public class DatePollMetaInf {
    //Titel, Ort, Beschreibung
    private String titel;
    private Beschreibung beschreibung;
    private Ort ort;
}
