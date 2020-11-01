package fr.formation.uywback.repository;

import fr.formation.uywback.models.game.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameUserRepository extends JpaRepository<GameUser, Long> {
}
