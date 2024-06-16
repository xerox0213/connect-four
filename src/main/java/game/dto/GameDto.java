package game.dto;

import game.model.Token;

import java.util.List;

public record GameDto(List<PlayerDto> players, Token[][] tokens, Token currPlayer, boolean isMyTurn) {
}
