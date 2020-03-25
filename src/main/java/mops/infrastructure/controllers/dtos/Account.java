package mops.infrastructure.controllers.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Account implements Serializable {

    public static final long serialVersionUID = 9784764376434L;

    private final String name; // Name der angemeldeten Person
    private final String email; // E-Mail-Adresse
    private final String image; // Bild (kann für jeden null sein)
    private final Set<String> roles; // Rollen der Person
}
