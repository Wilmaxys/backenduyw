package fr.formation.uywback.models.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "games")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 120)
	private String name;

	private boolean finished;

	@OneToMany(mappedBy = "game")
	private List<GameUser> GameUser;

	public Game() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<fr.formation.uywback.models.game.GameUser> getGameUser() {
		return GameUser;
	}

	public void setGameUser(List<fr.formation.uywback.models.game.GameUser> gameUser) {
		GameUser = gameUser;
	}

	public void addGameUser(fr.formation.uywback.models.game.GameUser gameUser) {
		this.GameUser.add(gameUser);
	}
}
