package fr.formation.uywback.controllers;

import fr.formation.uywback.models.User;
import fr.formation.uywback.models.game.Game;
import fr.formation.uywback.models.game.GameUser;
import fr.formation.uywback.payload.request.JoinRequest;
import fr.formation.uywback.payload.request.LoginRequest;
import fr.formation.uywback.repository.GameRepository;
import fr.formation.uywback.repository.GameUserRepository;
import fr.formation.uywback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameUserRepository gameUserRepository;

    @GetMapping("/all")
    public List<Game> showAll() {
        List<Game> listGame = gameRepository.findAll();

        return listGame;
    }

    @GetMapping("/{id}/players")
    public List<User> getAllPlayers(@Valid @RequestBody JoinRequest joinRequest) {
        Game game = gameRepository.findById(joinRequest.getIdGame()).orElseThrow(() -> new NullPointerException());
        
        List<User> listPlayers = new ArrayList<>();

        for (GameUser gUser : game.getGameUser()) {
            listPlayers.add(gUser.getUser());
        }
        
        return listPlayers;
    }

    @PostMapping("/{id}/join")
    public String join(@Valid @RequestBody JoinRequest joinRequest) {
        return "etette";
        //Game game = gameRepository.findById(joinRequest.getIdGame()).orElseThrow(() -> new NullPointerException());
        //User user = userRepository.findByUsername(joinRequest.getUsername()).orElseThrow(() -> new NullPointerException());
//
        //GameUser newGameUser = new GameUser();
        //newGameUser.setUser(user);
        //newGameUser.setGame(game);
//
        //user.addGameUser(newGameUser);
        //game.addGameUser(newGameUser);
//
        //userRepository.save(user);
        //gameRepository.save(game);
        //gameUserRepository.save(newGameUser);
    }
}
