package com.gamebasic.ranking.dto;

import lombok.Getter;

@Getter
public class RankingMeta {
    private RankingSeason season;
    private String generatedAt;
    private int schemaVersion;
    private int totalRecords;
}
