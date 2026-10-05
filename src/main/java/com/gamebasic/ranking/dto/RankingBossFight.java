package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingBossFight {
    private List<RankingBossPhase> phases;
    private String finishingCard;
    private int totalTurns;
}
