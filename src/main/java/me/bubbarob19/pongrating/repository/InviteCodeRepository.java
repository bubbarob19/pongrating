package me.bubbarob19.pongrating.repository;

import me.bubbarob19.pongrating.model.InviteCode;
import me.bubbarob19.pongrating.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InviteCodeRepository extends MongoRepository<InviteCode, String> {
}
