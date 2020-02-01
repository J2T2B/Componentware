package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CommandDelayer<T> {

    long delay;
    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    Runnable function;
    T command;

    /**
     *
     * @param command zu sendender Befehl
     * @param delay in Sekunden
     * @param function
     */
    public CommandDelayer(T command, long delay, Runnable function) {
        this.command = command;
        this.delay = delay;
        this.function = function;
        timer.schedule(function, delay, TimeUnit.SECONDS);
    }
}
