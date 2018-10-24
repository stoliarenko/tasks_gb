package ru.stoliarenko.gb.java3_lesson3;

import static org.junit.Assert.assertEquals;

import java.util.Deque;

import org.junit.Test;

public final class LogWriterTest {
    @Test
    public void lineNumberTest() {
        LogWriter lw = new LogWriter("TestUser");
        lw.clearLogs(); // writes one empty line
        lw.logLine("Line 1");
        lw.logLine("Line 2");
        assertEquals(lw.getLines().size(), 3);
        for(int i = 0; i < 132; i++) {
            lw.logLine("line " + i);
        }
        assertEquals(lw.getLines().size(), 100);
    }
    
    @Test
    public void contentTest() {
        LogWriter lw = new LogWriter("TestUser");
        lw.clearLogs();
        for(int i = 0; i <= 500; i++) {
            lw.logLine("line " + i);
        }
        Deque<String> lines = lw.getLines();
        assertEquals(lines.getFirst(), "line 401");
        assertEquals(lines.getLast(), "line 500");
    }
}
