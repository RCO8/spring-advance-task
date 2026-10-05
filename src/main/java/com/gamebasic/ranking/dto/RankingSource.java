package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingSource {
    private RankingMeta meta;
    private List<RankingRecord> records;
}
