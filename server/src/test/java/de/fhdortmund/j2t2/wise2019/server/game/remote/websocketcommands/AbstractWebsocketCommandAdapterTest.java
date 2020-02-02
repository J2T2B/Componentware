package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class AbstractWebsocketCommandAdapterTest {
    private final static ClassLoader CLASS_LOADER = AbstractWebsocketCommandAdapterTest.class.getClassLoader();

    private final static File READ_MESSAGE_COMMAND_FILE = new File(CLASS_LOADER.getResource("readmessagecommand.json").getPath());
    private final static File REINIT_COMMAND_FILE = new File(CLASS_LOADER.getResource("reinitcommand.json").getPath());
    private final static File SUBMIT_ANSWER_COMMAND_FILE = new File(CLASS_LOADER.getResource("submitanswercommand.json").getPath());

    private Gson gson;

    @Before
    public void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractWebSocketCommand.class, new AbstractWebsocketCommandAdapter());
        gson = gsonBuilder.create();
    }

    @Test
    public void reinitTest() throws IOException {
        String json = readFile(REINIT_COMMAND_FILE);
        AbstractWebSocketCommand command = gson.fromJson(json, AbstractWebSocketCommand.class);

        ReinitWebSocketCommand reinitWebSocketCommand = (ReinitWebSocketCommand) command;
        assertEquals("Reinit", reinitWebSocketCommand.getCommand());
    }

    @Test
    public void readMessageTest() throws IOException {
        String json = readFile(READ_MESSAGE_COMMAND_FILE);
        AbstractWebSocketCommand command = gson.fromJson(json, AbstractWebSocketCommand.class);

        ReadMessageWebSocketCommand readMessageWebSocketCommand = (ReadMessageWebSocketCommand) command;
        assertEquals("ReadMessage", readMessageWebSocketCommand.getCommand());
        assertEquals("xkcd", readMessageWebSocketCommand.getMessageId());
    }

    @Test
    public void submitAnswerTest() throws IOException {
        String json = readFile(SUBMIT_ANSWER_COMMAND_FILE);
        AbstractWebSocketCommand command = gson.fromJson(json, AbstractWebSocketCommand.class);

        SubmitAnswerWebSocketCommand submitAnswerWebSocketCommand = (SubmitAnswerWebSocketCommand) command;
        assertEquals("SubmitAnswer", submitAnswerWebSocketCommand.getCommand());
        assertEquals(42, submitAnswerWebSocketCommand.getAnswerId());
    }

    private String readFile(File file) throws IOException {
        return Files.asCharSource(file, StandardCharsets.UTF_8).read();
    }

}
