package com.gamebasic.game.entity;

import com.gamebasic.runcard.dto.CardResponse;
import lombok.Getter;

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

    public GameSummaryResponse(Long id, String playerName, int currentHp, int currentFloor, GamePhase phase, GameStatus status,  List<CardResponse> cards) {
        this.id = id;
        this.playerName = playerName;
        this.currentHp = currentHp;
        this.currentFloor = currentFloor;
        this.phase = phase;
        this.status = status;
        this.cards = cards;
    }
}
