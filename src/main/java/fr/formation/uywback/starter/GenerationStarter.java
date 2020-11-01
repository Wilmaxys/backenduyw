package fr.formation.uywback.starter;

import fr.formation.uywback.models.ERole;
import fr.formation.uywback.models.Role;
import fr.formation.uywback.models.game.Game;
import fr.formation.uywback.models.game.GameUser;
import fr.formation.uywback.repository.GameRepository;
import fr.formation.uywback.repository.GameUserRepository;
import fr.formation.uywback.repository.RoleRepository;
import fr.formation.uywback.repository.UserRepository;
import fr.formation.uywback.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("starter") //DISPO QUE SI ON ACTIVE LE PROFIL STARTER
public class GenerationStarter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameUserRepository gameUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private PasswordEncoder pwEncoder;

    @PostConstruct
    public void init(){
        Role newRole = new Role();
        newRole.setName(ERole.ROLE_USER);
        roleRepository.save(newRole);

        Role newRole2 = new Role();
        newRole2.setName(ERole.ROLE_ADMIN);
        roleRepository.save(newRole2);

        Role newRole3 = new Role();
        newRole3.setName(ERole.ROLE_MODERATOR);
        roleRepository.save(newRole3);

        // Create new user's account
        User user = new User("admin",
               "admin@admin.fr",
                encoder.encode("admin"));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);

        Game newGame = new Game();
        newGame.setName("test");
        newGame.setFinished(false);

        GameUser newGameUser = new GameUser();
        newGameUser.setUser(user);
        newGameUser.setGame(newGame);

        List<GameUser> listGameUser = new ArrayList<>();
        listGameUser.add(newGameUser);

        user.setGameUser(listGameUser);
        newGame.setGameUser(listGameUser);

        userRepository.save(user);
        gameRepository.save(newGame);
        gameUserRepository.save(newGameUser);
    }
}
