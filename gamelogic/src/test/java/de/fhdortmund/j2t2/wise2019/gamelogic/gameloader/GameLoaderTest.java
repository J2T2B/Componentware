package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

public class GameLoaderTest {

    Callable<InputStream> testJsonReadableProvider = () -> Objects.requireNonNull(GameLoaderTest.class.getClassLoader().getResource("game.json")).openStream();
    BiFunction<TestAnswer, Integer, BiConsumer<TestMessage, TestMessage[]>> resolveFunction = (answer, id) -> {
        ((TestAnswer) answer).id = id;
        return (parent, targets) -> {
            ((TestAnswer)answer).parent = parent;
            ((TestAnswer)answer).targets = Arrays.asList(targets);
        };
    };

    public GameLoaderTest() throws IOException {
    }

    @Test
    public void loadGame() throws GameLoadingException {
        GameLoader gameLoader = new GameLoader(testJsonReadableProvider);
        Map<String, TestMessage> messages = gameLoader.loadGame(TestMessage[].class, resolveFunction);

        Message maximalConfiguration = messages.get("test1");
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

        Answer[] answers = maximalConfiguration.getAnswers().toArray(new Answer[0]);
        Answer toTest = answers[0];
            assertEquals(0, toTest.getId());
            assertEquals("Stäckt der Stecker?", toTest.getText());
            assertArrayEquals(new String[]{"test1", "test2"}, toTest.getTargets().stream().map(Message::getId).toArray(String[]::new));

        toTest = answers[1];
            assertEquals(1, toTest.getId());
            assertEquals("I'm sorry Dave, I'm afraid I can't do that", toTest.getText());
            assertArrayEquals(new String[]{"test2"}, toTest
                    .getTargets()
                    .stream()
                    .map(Message::getId)
                    .toArray(String[]::new));


        Message minimalConfiguration = messages.get("test2");
            assertEquals("test2", minimalConfiguration.getId());
            assertEquals("What is the answer to the Ultimate Question of Life, the Universe, and Everything", minimalConfiguration.getText());
            assertArrayEquals(new TestAnswer[0], minimalConfiguration.getAnswers().toArray(new Answer[0]));
    }
}