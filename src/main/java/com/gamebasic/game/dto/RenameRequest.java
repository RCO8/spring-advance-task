package com.gamebasic.game.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class RenameRequest {
    @Column(nullable = false, length = 12)
    private String playerName;
}
