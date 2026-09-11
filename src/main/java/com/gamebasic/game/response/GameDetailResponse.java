package com.gamebasic.game.response;

import com.gamebasic.game.entity.GamePhase;
import com.gamebasic.game.entity.GameStatus;
import com.gamebasic.runcard.dto.CardResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GameDetailResponse {
    private final Long id;
    private final String playerName;
    private final int currentHp;
    private final int currentFloor;
    private final GamePhase phase;
    private final GameStatus status;
    private final List<CardResponse> deck;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final Long deckSize;

    public GameDetailResponse(
        Long id,
        String playerName,
        int currentHp,
        int currentFloor,
        GamePhase phase,
        GameStatus status,
        LocalDateTime createAt,
        LocalDateTime updateAt,
        List<CardResponse> deck,
        Long deckSize) {
        this.id = id;
        this.playerName = playerName;
        this.currentHp = currentHp;
        this.currentFloor = currentFloor;
        this.phase = phase;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deck = List.copyOf(deck);
        this.deckSize = deckSize;
    }
}
