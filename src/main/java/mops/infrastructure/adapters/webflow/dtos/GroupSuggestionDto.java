package mops.infrastructure.adapters.webflow.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GroupSuggestionDto implements Serializable {

    public static final long serialVersionUID = 4350983258749L;

    private String value;
    private String label;
}
