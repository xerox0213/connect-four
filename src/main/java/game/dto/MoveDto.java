package game.dto;

import game.model.Token;

public record MoveDto(Token token, int columnIndex, int rowIndex) {
}
