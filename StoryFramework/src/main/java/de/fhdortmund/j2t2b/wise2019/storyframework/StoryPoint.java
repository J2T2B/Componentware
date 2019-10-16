package de.fhdortmund.j2t2b.wise2019.storyframework;

import java.util.HashMap;
import java.util.Map;

/**
 * A decision point in a story for a user to decide how to continue
 */
public class StoryPoint {

    private Map<Option, StoryPoint> nextPoints = new HashMap<>();

    /**
     * How long to wait until the text of this story point is send after entering it in milliseconds
     */
    private long actionDelay = 0;

    /**
     * The text the npc sends to the user
     */
    private String text;

    public StoryPoint(String text, long actionDelay){
        this.text = text;
        this.actionDelay = actionDelay;
    }

    public String getTextDelayed(){
        try {
            wait(actionDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return text;
    }

    public StoryPoint getNextStoryPoint(Option chosenOption){
        return nextPoints.get(chosenOption);
    }

    public void addStoryPoint(Option option, StoryPoint storyPoint){
        nextPoints.put(option, storyPoint);
    }
}
