package ru.stoliarenko.gb.lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Race {
    private final ArrayList<Stage> stages;
    private final Integer participants;
    private CountDownLatch countdown;
    private boolean hasAWinner = false;
    
    public ArrayList<Stage> getStages() { return stages; }
    public CountDownLatch getCountdown() { return countdown; }
    public void resetCountdown() { countdown = new CountDownLatch(participants); }
    public boolean finish() {
        if (!hasAWinner) {
            hasAWinner = true;
            return true;
        }
        return false;
    }
    
    public Race(final Integer participants, final Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.countdown = new CountDownLatch(participants);
        this.participants = participants;
    }
}
