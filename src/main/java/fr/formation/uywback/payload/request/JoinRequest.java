package fr.formation.uywback.payload.request;

import javax.validation.constraints.NotBlank;

public class JoinRequest {

    private long idGame;

    @NotBlank
    private String username;

    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
