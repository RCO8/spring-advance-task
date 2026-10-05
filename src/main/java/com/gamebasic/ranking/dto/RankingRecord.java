package com.gamebasic.ranking.dto;

import lombok.Getter;

@Getter
public class RankingRecord {
    private Long id;
    private String submittedAt;
    private RankingClientInfo client;
    private RankingPlayer player;
    private RankingRun run;
    private RankingBossFight bossFight;
    private RankingDeck deck;
}
