package fr.formation.uywback.payload.request;

import javax.validation.constraints.NotBlank;

public class GameRequest {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
