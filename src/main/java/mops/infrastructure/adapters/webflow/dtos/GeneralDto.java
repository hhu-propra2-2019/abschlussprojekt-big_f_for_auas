package mops.infrastructure.adapters.webflow.dtos;

import lombok.Getter;

// Um die nicht gemappten Fehlermeldungen einfacher anzeigen zu können, muss jedes Dto hiervon erben.
// Der Wert von defaulterrors ist unwichtig, das Feld muss nur vorhanden sein.
@Getter
public class GeneralDto {
    private final boolean defaulterrors;

    public GeneralDto() {
        defaulterrors = false;
    }
}
