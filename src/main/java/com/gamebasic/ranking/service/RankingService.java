package com.gamebasic.ranking.service;

import com.gamebasic.ranking.CardType;
import com.gamebasic.ranking.client.RankingClient;
import com.gamebasic.ranking.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RankingService {
    private static final String CLEARED = "CLEARED";
    private static final int TOTAL_FLOORS = 10;
    private static final int MIN_SECONDS_PER_FLOOR = 30;
    private static final int MAX_HP = 99;
    private static final int MIN_DECK_SIZE = 9;
    private static final int MAX_DECK_SIZE = 20;
    private static final int LAST_REWARD_FLOOR = 9;
    private static final List<String> BOSS_PHASES = List.of("THRONE", "UNBOUND", "ECLIPSE");

    private final RankingClient rankingClient;

    public RankingResponse getRankings() {
        RankingSource source = rankingClient.fetch();

        List<RankingRecord> candidates = new ArrayList<>();
        for (RankingRecord record : source.getRecords()) {
            RankingRun run = record.getRun();
            if (CLEARED.equals(run.getStatus()) && run.getClearedFloor() == TOTAL_FLOORS) {
                candidates.add(record);
            }
        }

        List<RankingRecord> validRecords = new ArrayList<>();
        for (RankingRecord record : candidates) {
            if (isValid(record)) {
                validRecords.add(record);
            }
        }

        validRecords.sort(
                Comparator.comparingInt((RankingRecord record) -> record.getRun().getDurationSeconds())
                        .thenComparing(record -> record.getRun().getFinalHp(), Comparator.reverseOrder())
                        .thenComparingLong(RankingRecord::getId)
        );

        Set<String> rankedPlayerIds = new HashSet<>();
        List<RankingEntryResponse> entries = new ArrayList<>();
        for (RankingRecord record : validRecords) {
            String playerId = record.getPlayer().getId();
            if (rankedPlayerIds.contains(playerId)) {
                continue;
            }
            rankedPlayerIds.add(playerId);
            entries.add(new RankingEntryResponse(
                    entries.size() + 1,
                    record.getPlayer().getName(),
                    record.getRun().getDurationSeconds(),
                    record.getRun().getFinalHp(),
                    record.getBossFight().getTotalTurns(),
                    record.getDeck().getCards().size()
            ));
        }

        return new RankingResponse(
                source.getMeta().getSeason().getId(),
                source.getRecords().size(),
                candidates.size() - validRecords.size(),
                entries
        );
    }

    private boolean isValid(RankingRecord record) {
        RankingRun run = record.getRun();
        if (run.getDurationSeconds() < run.getClearedFloor() * MIN_SECONDS_PER_FLOOR) {
            return false;
        }
        if (run.getFinalHp() < 1 || run.getFinalHp() > MAX_HP) {
            return false;
        }

        List<RankingCard> cards = record.getDeck().getCards();
        if (cards.size() < MIN_DECK_SIZE || cards.size() > MAX_DECK_SIZE) {
            return false;
        }
        if (record.getDeck().getSize() != cards.size()) {
            return false;
        }
        Set<String> cardTypesInDeck = new HashSet<>();
        for (RankingCard card : cards) {
            if (!isKnownCardType(card.getCardType())) {
                return false;
            }
            if (card.getAcquiredFloor() < 0 || card.getAcquiredFloor() > LAST_REWARD_FLOOR) {
                return false;
            }
            cardTypesInDeck.add(card.getCardType());
        }

        RankingBossFight bossFight = record.getBossFight();
        if (bossFight == null || !cardTypesInDeck.contains(bossFight.getFinishingCard())) {
            return false;
        }
        List<RankingBossPhase> phases = bossFight.getPhases();
        if (phases == null || phases.size() != BOSS_PHASES.size()) {
            return false;
        }
        int phaseTurns = 0;
        for (int i = 0; i < phases.size(); i++) {
            if (!BOSS_PHASES.get(i).equals(phases.get(i).getPhase())) {
                return false;
            }
            if (phases.get(i).getTurns() < 1) {
                return false;
            }
            phaseTurns += phases.get(i).getTurns();
        }
        return bossFight.getTotalTurns() == phaseTurns;
    }

    private boolean isKnownCardType(String cardType) {
        for (CardType type : CardType.values()) {
            if (type.name().equals(cardType)) {
                return true;
            }
        }
        return false;
    }
}
