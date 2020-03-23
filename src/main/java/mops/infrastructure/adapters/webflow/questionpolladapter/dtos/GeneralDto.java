package mops.infrastructure.adapters.webflow.questionpolladapter.dtos;

import lombok.Getter;

//TODO: Zusammenlegen mit GeneralDto aus datepolladapter/dtos

// Um die nicht gemappten Fehlermeldungen einfacher anzeigen zu können, muss jedes Dto hiervon erben.
// Der Wert von defaulterrors ist unwichtig, das Feld muss nur vorhanden sein.
@Getter
class GeneralDto {
    private final boolean defaulterrors;

    /* default */ GeneralDto() {
        defaulterrors = false;
    }
}
