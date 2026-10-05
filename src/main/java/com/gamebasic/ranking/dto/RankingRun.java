package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingRun {
    private String seed;
    private String status;
    private int clearedFloor;
    private int durationSeconds;
    private int finalHp;
    private List<RankingFloor> floors;
}
