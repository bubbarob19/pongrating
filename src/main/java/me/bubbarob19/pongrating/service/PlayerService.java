package me.bubbarob19.pongrating.service;

import lombok.AllArgsConstructor;
import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.Rank;
import me.bubbarob19.pongrating.model.User;
import me.bubbarob19.pongrating.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(String id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayerByFirstNameAndLastName(String firstName, String lastName) {
        return playerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Player addPlayer(Player player) {
        return player;
    }

    public void createPlayer(User user) {
        Player player = Player.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getPlayerId())
                .wins(0)
                .losses(0)
                .elo(1200)
                .rank(Rank.NEWBIE)
                .build();
        playerRepository.save(player);
    }

    public Player updatePlayer(String id, Player player) {
        player.setId(id);
        return playerRepository.save(player);
    }

    public void deletePlayer(String id) {
        playerRepository.deleteById(id);
    }
}