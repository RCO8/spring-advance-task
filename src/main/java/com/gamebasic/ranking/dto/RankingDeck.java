package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingDeck {
    private int size;
    private List<RankingCard> cards;
}
