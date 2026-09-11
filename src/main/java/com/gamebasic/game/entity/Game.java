package com.gamebasic.game.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "games")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 12)
    private String playerName;

    @Column(nullable = false)
    private int currentHp;

    @Column(nullable = false)
    private int currentFloor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private GamePhase phase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private GameStatus status;

    @Column(nullable = false)
    private int deckSize;

    public Game(String playerName) {
        this.playerName = playerName;
        this.currentHp = 99;
        this.currentFloor = 1;
        this.phase = GamePhase.REWARD;
        this.status = GameStatus.PLAYING;
    }

    public void rename(String playerName) {
        this.playerName = playerName;
    }

    public void updateProgress(
        int currentHp,
        int currentFloor,
        GamePhase phase,
        GameStatus status
    ) {
        this.currentHp = currentHp;
        this.currentFloor = currentFloor;
        this.phase = phase;
        this.status = status;
    }

    public boolean isFinished() {
        return status != GameStatus.PLAYING;
    }
}
