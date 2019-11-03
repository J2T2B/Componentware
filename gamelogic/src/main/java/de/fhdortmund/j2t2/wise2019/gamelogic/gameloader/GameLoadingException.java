package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import java.io.IOException;

public class GameLoadingException extends Exception {
    public GameLoadingException(String message, Throwable reason) {
        super(message, reason);
    }
}
