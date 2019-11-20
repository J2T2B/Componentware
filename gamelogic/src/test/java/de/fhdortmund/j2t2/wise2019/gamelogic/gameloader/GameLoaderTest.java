package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models.AnswerJson;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models.MessageJson;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class GameLoaderTest {

    String testJsonReadable = Objects.requireNonNull(GameLoaderTest.class.getClassLoader().getResource("game.json")).getFile();

    @Test
    public void loadGame() throws GameLoadingException {
        GameLoader gameLoader = new GameLoader(testJsonReadable);
        List<? extends MessageDetailed> messages = gameLoader.loadGame();

        assertSame(messages, gameLoader.loadGame());

        MessageDetailed maximalConfiguration = messages.get(0);
            assertNotNull(maximalConfiguration);
            assertEquals("Open the pod bay doors, Hal.", maximalConfiguration.getText());
            assertEquals("test1", maximalConfiguration.getId());
            assertEquals(500, maximalConfiguration.getDelay());
            assertTrue(maximalConfiguration.isRoot());
            assertEquals(1.5, maximalConfiguration.getProbably(), 0.0);

        Points points = maximalConfiguration.getPoints();
            assertNotNull(points);
            assertEquals(0, points.getBudget());
            assertEquals(0, points.getChefSatisfaction());
            assertEquals(0, points.getCustomerExperience());

        AnswerDetailed[] answers = maximalConfiguration.getAnswers().toArray(new AnswerDetailed[0]);
        AnswerDetailed toTest = answers[0];
            assertEquals(1, toTest.getId());
            assertEquals("Stäckt der Stecker?", toTest.getText());
            assertArrayEquals(new String[]{"test2-1", "test2-2"}, toTest.getTargets());

        toTest = answers[1];
            assertEquals(2, toTest.getId());
            assertEquals("I'm sorry Dave, I'm afraid I can't do that", toTest.getText());
            assertArrayEquals(new String[]{"test3"}, toTest.getTargets());


        MessageDetailed minimalConfiguration = messages.get(1);
            assertEquals("test2", minimalConfiguration.getId());
            assertEquals("What is the answer to the Ultimate Question of Life, the Universe, and Everything", minimalConfiguration.getText());
            assertArrayEquals(new AnswerJson[0], minimalConfiguration.getAnswers().toArray(new AnswerDetailed[0]));
    }
}