package com.gamebasic.game.service;

import com.gamebasic.game.dto.CreateRequest;
import com.gamebasic.game.response.GameDetailResponse;
import com.gamebasic.game.dto.ProgressRequest;
import com.gamebasic.game.dto.RenameRequest;
import com.gamebasic.game.entity.Game;
import com.gamebasic.game.entity.GameSummaryResponse;
import com.gamebasic.game.repository.GameRepository;
import com.gamebasic.game.response.GameDetailResponse;
import com.gamebasic.runcard.dto.CardResponse;
import com.gamebasic.runcard.dto.RunCardRequest;
import com.gamebasic.runcard.entity.RunCard;
import com.gamebasic.runcard.repository.RunCardRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final RunCardRepository runCardRepository;

    @Transactional(readOnly = true)
    public GameDetailResponse createGame(CreateRequest request) {
        Game game = gameRepository.save(new Game(request.getPlayerName()));
        gameRepository.save(game);
        saveDeck(game, request.getDeck());
        return toGameDetailResponse(game);
    }

    private void saveDeck(Game game, List<RunCardRequest> deck) {
        List<RunCard> cards = new ArrayList<>();
        for (RunCardRequest card : deck) {
            cards.add(
                    new RunCard(
                            game,
                            card.getCardType(),
                            card.getAcquiredFloor())
            );
        }
        runCardRepository.saveAll(cards);
    }

    private Game findGame(Long gameId) {
        return gameRepository.findById(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게임을 찾을 수 없습니다"));
    }

    @Transactional
    public GameDetailResponse updateProgress(Long gameId, ProgressRequest request) {
        Game game = findGame(gameId);

        if (game.isFinished()) { // 덮어씌우지 못하게
            throw new ResponseStatusException(HttpStatus.CONFLICT, "게임이 진행중이 아닙니다.");
        }
        game.updateProgress(
            request.getCurrentHp(),
            request.getCurrentFloor(),
            request.getPhase(),
            request.getStatus()
        );
        // 요청의 deck은 저장할 덱 전체이므로 기존 카드를 모두 지우고 요청 순서대로 다시 저장합니다.
        runCardRepository.deleteAllByGame(game);
        saveDeck(game, request.getDeck());
        return toGameDetailResponse(game);
    }

     // TODO (Lv 7): 게임 목록 조회. 주석을 풀고 구현하세요.
     @Transactional(readOnly = true)
     public List<GameSummaryResponse> getGames() {
        List<Game> games = gameRepository.findAllByOrderByIdAsc();
        List<GameSummaryResponse> gamesSummary = new ArrayList<>();

        for(Game g : games){
            List<CardResponse> deck = findDeck(g); //이거 해당 Id에서 가져오기
            gamesSummary.add(new GameSummaryResponse(
                    g.getId(),
                    g.getPlayerName(),
                    g.getCurrentHp(),
                    g.getCurrentFloor(),
                    g.getPhase(),
                    g.getStatus(),
                    deck
            ));
        }
        return gamesSummary;
     }

     // TODO (Lv 7): 게임 상세 조회. 주석을 풀고 구현하세요.
     @Transactional(readOnly = true)
     public GameDetailResponse getGame(Long gameId) {
        Game game = findGame(gameId);
        return toGameDetailResponse(game);
     }

     // 카드 덱 불러오기
     private List<CardResponse> findDeck(Game game) {
        List<RunCard> cards  = runCardRepository.findAllByGameOrderByIdAsc(game);
        List<CardResponse> deck = new ArrayList<>();
        for(RunCard card : cards) {
            deck.add(new CardResponse(
                    card.getId(),
                    card.getCardType(),
                    card.getAcquiredFloor()
            ));
        }
        return deck;
     }

     // 게임 Dto 불러오기
     private GameDetailResponse toGameDetailResponse(Game game) {
        List<CardResponse> deck = findDeck(game);

        return new GameDetailResponse(
                game.getId(),
                game.getPlayerName(),
                game.getCurrentHp(),
                game.getCurrentFloor(),
                game.getPhase(),
                game.getStatus(),
                deck
        );
     }

    // TODO (Lv 8): 플레이어 이름 변경 — 변경 감지로 수정
    @Transactional
    public GameDetailResponse remaneGame(Long gameId, @Valid RenameRequest request) {
        Game game = findGame(gameId);
        game.rename(request.getPlayerName());
        return toGameDetailResponse(game);
    }

    // TODO (Lv 8): 게임 삭제
    @Transactional
    public void deleteGame(Long gameId) {
        Game game = findGame(gameId);
        runCardRepository.deleteAllByGame(game);
        gameRepository.delete(game);
    }
}
