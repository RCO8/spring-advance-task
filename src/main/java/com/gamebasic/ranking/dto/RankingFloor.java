package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingFloor {
    private int floor;
    private String enemy;
    private int turns;
    private int hpAfter;
    private List<RankingReward> rewards;
}
