package com.gamebasic.ranking.dto;

import lombok.Getter;

@Getter
public class RankingEntryResponse {
    private final int rank;
    private final String playerName;
    private final int clearTimeSeconds;
    private final int remainingHp;
    private final int bossTurns;
    private final int deckSize;

    public RankingEntryResponse(int rank, String playerName, int clearTimeSeconds, int remainingHp, int bossTurns, int deckSize) {
        this.rank = rank;
        this.playerName = playerName;
        this.clearTimeSeconds = clearTimeSeconds;
        this.remainingHp = remainingHp;
        this.bossTurns = bossTurns;
        this.deckSize = deckSize;
    }
}
