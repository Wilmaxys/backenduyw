package fr.formation.uywback.controllers;

import fr.formation.uywback.models.User;
import fr.formation.uywback.models.game.Game;
import fr.formation.uywback.models.game.GameUser;
import fr.formation.uywback.payload.request.GameRequest;
import fr.formation.uywback.payload.request.JoinRequest;
import fr.formation.uywback.payload.request.UnjoinAllRequest;
import fr.formation.uywback.repository.GameRepository;
import fr.formation.uywback.repository.GameUserRepository;
import fr.formation.uywback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/games")
public class GameController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameUserRepository gameUserRepository;

    public GameController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/all")
    public List<Game> showAll() {
        List<Game> listGame = gameRepository.findAll();

        return listGame;
    }

    @PostMapping("/create")
    public Game createGame(@Valid @RequestBody GameRequest gameRequest) {
        Game game = new Game();
        game.setName(gameRequest.getName());
        gameRepository.save(game);

        return game;
    }

    @GetMapping("/{id}/players")
    public List<User> getAllPlayers(@PathVariable("id") long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new NullPointerException());
        
        List<User> listPlayers = new ArrayList<>();

        for (GameUser gUser : game.getGameUser()) {
            listPlayers.add(gUser.getUser());
        }
        
        return listPlayers;
    }

    @PostMapping("/{id}/join")
    public boolean join(@Valid @RequestBody JoinRequest joinRequest) {
        System.out.println(joinRequest.getIdGame());
        System.out.println(joinRequest.getUsername());

        try{
            Game game = gameRepository.findById(joinRequest.getIdGame()).orElseThrow(() -> new NullPointerException());
            User user = userRepository.findByUsername(joinRequest.getUsername()).orElseThrow(() -> new NullPointerException());

            GameUser newGameUser = new GameUser();
            newGameUser.setUser(user);
            newGameUser.setGame(game);

            user.addGameUser(newGameUser);
            game.addGameUser(newGameUser);

            gameUserRepository.save(newGameUser);
            userRepository.save(user);
            gameRepository.save(game);

            this.simpMessagingTemplate.convertAndSend("/game/" + joinRequest.getIdGame() + "/joined", joinRequest.getUsername());

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @PostMapping("/unjoinAll")
    public boolean unjoinAll(@Valid @RequestBody UnjoinAllRequest unjoinAllRequest) {
        try{
            User user = userRepository.findByUsername(unjoinAllRequest.getUsername()).orElseThrow(() -> new NullPointerException());

            for (GameUser guser: user.getGameUser()) {
                gameUserRepository.delete(guser);
            }

            //gameUserRepository.deleteByUser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
