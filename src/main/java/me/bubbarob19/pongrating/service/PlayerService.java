package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.Rank;
import me.bubbarob19.pongrating.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(String id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayerByFirstNameAndLastName(String firstName, String lastName) {
        return playerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Player> getPlayersByFirstName(String firstName) {
        return playerRepository.findByFirstName(firstName);
    }

    public List<Player> getPlayersByLastName(String lastName) {
        return playerRepository.findByLastName(lastName);
    }

    public List<Player> getPlayersByElo(int elo) {
        return playerRepository.findByElo(elo);
    }

    public List<Player> getPlayersWithEloGreaterThan(int elo) {
        return playerRepository.findPlayersWithEloGreaterThan(elo);
    }

    public Player addPlayer(Player player) {
        player.setElo(1400);
        player.setRank(Rank.NEWBIE);
        return playerRepository.save(player);
    }

    public Player updatePlayer(String id, Player player) {
        player.setId(id);
        return playerRepository.save(player);
    }

    public void deletePlayer(String id) {
        playerRepository.deleteById(id);
    }
}