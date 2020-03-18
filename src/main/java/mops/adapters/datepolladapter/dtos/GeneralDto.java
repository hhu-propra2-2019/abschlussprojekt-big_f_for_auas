package mops.adapters.datepolladapter.dtos;

import lombok.Getter;

// Um die nicht gemappten Fehlermeldungen einfacher anzeigen zu können, muss jedes Dto hiervon erben.
// Der Wert von defaulterrors ist unwichtig, das Feld muss nur vorhanden sein.
@Getter
class GeneralDto {
    private final boolean defaulterrors;

    /* default */ GeneralDto() {
        defaulterrors = false;
    }
}
