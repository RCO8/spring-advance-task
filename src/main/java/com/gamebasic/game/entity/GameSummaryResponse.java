package com.gamebasic.game.entity;

import com.gamebasic.runcard.dto.CardResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GameSummaryResponse {
    private final Long id;
    private final String playerName;
    private final int currentHp;
    private final int currentFloor;
    private final GamePhase phase;
    private final GameStatus status;
    private final List<CardResponse> cards;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final int deckSize;


    public GameSummaryResponse(
            Long id, String playerName,
            int currentHp, int currentFloor,
            GamePhase phase, GameStatus status,
            LocalDateTime createAt, LocalDateTime updateAt,
            List<CardResponse> cards, int deckSize) {
        this.id = id;
        this.playerName = playerName;
        this.currentHp = currentHp;
        this.currentFloor = currentFloor;
        this.phase = phase;
        this.status = status;
        this.cards = cards;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deckSize = deckSize;
    }
}
