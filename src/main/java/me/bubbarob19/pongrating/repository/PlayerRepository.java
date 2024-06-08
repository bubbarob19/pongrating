package me.bubbarob19.pongrating.repository;

import me.bubbarob19.pongrating.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<Player> findByFirstNameAndLastName(String firstName, String lastName);

    List<Player> findByFirstName(String firstName);

    List<Player> findByLastName(String lastName);

    List<Player> findByElo(int elo);

    @Query("{ 'elo' : { $gt: ?0 } }")
    List<Player> findPlayersWithEloGreaterThan(int elo);
}
