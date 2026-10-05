package com.gamebasic.ranking.controller;

import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/rankings")
    public ResponseEntity<RankingResponse> getRankings() {
        return  ResponseEntity.ok(rankingService.getRankings());
    }
}
