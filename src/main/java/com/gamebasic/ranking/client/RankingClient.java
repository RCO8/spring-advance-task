package com.gamebasic.ranking.client;

import com.gamebasic.ranking.dto.RankingSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RankingClient {
    private static final String SOURCE_URL = "https://f-api.github.io/game-spring-api-docs/basic/rankings.json";
    private final RestClient restClient = RestClient.create();

    public RankingSource fetch() {
        return restClient.get()
                .uri(SOURCE_URL)
                .retrieve()
                .body(RankingSource.class);
    }
}
