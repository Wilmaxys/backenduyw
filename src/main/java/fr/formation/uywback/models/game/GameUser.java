package fr.formation.uywback.models.game;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.formation.uywback.models.User;
import fr.formation.uywback.models.game.Game;
import fr.formation.uywback.models.primary.GameUserPK;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "game_users")
@IdClass(GameUserPK.class)
public class GameUser {
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "pk")
    @JsonIgnore
    public String getPk() {
        return game.getId() + "->" + user.getId();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameUser gameUser = (GameUser) o;
        return Objects.equals(game, gameUser.game) &&
                Objects.equals(user, gameUser.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, user);
    }
}