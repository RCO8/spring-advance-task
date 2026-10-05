package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingPlayer {
    private String id;
    private String name;
    private String region;
    private List<String> tags;
}
