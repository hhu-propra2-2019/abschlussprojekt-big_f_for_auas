package mops.infrastructure.adapters.webflow.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GroupSuggestionDto implements Serializable {

    public static final long serialVersionUID = 4350983258749L;

    // Namen sind vorgegeben von tagsinput.js
    // Das Objekt wird von Thymeleaf zu JSON serialisiert: /*[[*{suggestions}]]*/
    private String value;
    private String label;
}
