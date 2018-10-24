package ru.stoliarenko.gb.java3_lesson3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Java3 lesson3 homework
 * 
 * Задание:
 * Написать класс для логирования ста последних строк чата
 * 
 * @author Stoliarenko Alexander
 */
public final class LogWriter {
    private static final Charset UTF8 = Charset.forName("utf-8");
    private final Path logFile;
    private final boolean valid;
    private final Deque<String> lines;
    
    /**
     * Конструктор в случае проблем с созданием/нахождением
     * файла логов текущего пользователя обозначает созданный класс
     * не валидным
     * 
     * @param username - имя пользователя для которого ведутся логи
     */
    public LogWriter(final String username) {
        logFile = Paths.get(String.format("/chatapp/userlogs/%s.txt", username));
        if(Files.notExists(logFile)) {
            try {
                Files.createDirectories(logFile.getParent());
                Files.createFile(logFile);
            } catch (IOException e) {
                System.out.println("Log file can not be created!");
                lines = null;
                valid = false;
                return;
            }
        }
        valid = true;
        lines = getLines();
    }
    
    /**
     * Возвращает все строки из файла логов текущего пользователя
     */
    public Deque<String> getLines(){
        if(!valid) return null;
        final Deque<String> resultList = new LinkedList<>();
        try {
            for (String line : Files.readAllLines(logFile, UTF8)) {
                resultList.addLast(line);
            }
        } catch (IOException fileProcessingException) {
            System.out.println("File reading problem!");
            fileProcessingException.printStackTrace();
        }
        return resultList;
    }
    /**
     * Добавляет строку в список логов пользователя, удаляет лишние строки если таковые имеются
     * и переписывает файл логов пользователя новыми данными
     */
    public void logLine(final String lineToLog) {
        if(!valid || lineToLog == null) return;
        lines.add(lineToLog);
        while(lines.size()>100)lines.removeFirst();
        try {
            Files.write(logFile, lines, UTF8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException fileProcessingException) {
            System.out.println("File writing problem!");
            fileProcessingException.printStackTrace();
        }
    }
    
    /**
     * Очищает список логов пользователя и прописывает в файл одну пустую строку
     */
    public void clearLogs() {
        if(!valid) return;
        lines.clear();
        logLine("");
    }
}
