package game.dto;

import game.oo.ConnectFourEvent;

import java.io.Serializable;

public record ConnectFourEventDto (ConnectFourEvent connectFourEvent, Object data) implements Serializable {

}
