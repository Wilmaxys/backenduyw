package fr.formation.uywback.models.primary;

import java.io.Serializable;
import java.util.Objects;

public class GameUserPK implements Serializable {

    private long game;
    private long user;

    public long getGame() {
        return game;
    }

    public void setGame(long game) {
        this.game = game;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameUserPK that = (GameUserPK) o;
        return game == that.game &&
                user == that.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, user);
    }

}