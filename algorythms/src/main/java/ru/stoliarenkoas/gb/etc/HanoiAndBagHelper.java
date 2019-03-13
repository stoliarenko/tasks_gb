package ru.stoliarenkoas.gb.etc;

import ru.stoliarenkoas.gb.etc.model.BagItem;
import ru.stoliarenkoas.gb.structures.MyStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HanoiAndBagHelper {

    /**
     * Generates algorithm to solve Hanoi puzzle with N elements
     *
     * @return list of moves needed to solve puzzle, each move is a byte array with two elements representing move start and end points
     */
    public static List<byte[]> getTowerMoveAlgorithm(int numberOfDiscs) {
        List<byte[]> result = new ArrayList<byte[]>();
        getSpecialTowerMoveAlgorithm(numberOfDiscs, (byte)1, (byte)3, result);
        return result;
    }

    private static void getSpecialTowerMoveAlgorithm(int numberOfDiscs, byte from, byte to, List<byte[]> resultList) {
        if (numberOfDiscs < 1) return;

        if (numberOfDiscs == 1) {
            resultList.add(new byte[]{from, to});
            System.out.printf("Move disc from %d, to %d.%n", from, to);
            return;
        }

        byte unusedPosition = (byte)(6 - from - to);
        getSpecialTowerMoveAlgorithm(numberOfDiscs - 1, from, unusedPosition, resultList);
        getSpecialTowerMoveAlgorithm(1, from, to, resultList);
        getSpecialTowerMoveAlgorithm(numberOfDiscs - 1, unusedPosition, to, resultList);
    }

    private static class Task {
        int numberOfDiscs;
        byte from;
        byte to;

        Task(int numberOfDiscs, byte from, byte to) {
            this.numberOfDiscs = numberOfDiscs;
            this.from = from;
            this.to = to;
        }
    }

    public static List<byte[]> getTowerMoveAlgorithmRecursively(int numberOfDiscs) {
        List<byte[]> result = new ArrayList<byte[]>();
        MyStack<Task> tasks = new MyStack<Task>();
        tasks.push(new Task(numberOfDiscs, (byte)1, (byte)3));
        getSpecialTowerMoveAlgorithmRecursively(result, tasks);
        return result;
    }

    private static void getSpecialTowerMoveAlgorithmRecursively(List<byte[]> resultList, MyStack<Task> tasks) {
        while (tasks.getSize() > 0) {
            Task currentTask = tasks.pop();
            if (currentTask.numberOfDiscs == 1) {
                resultList.add(new byte[]{currentTask.from, currentTask.to});
                System.out.printf("Move disc from %d, to %d.%n", currentTask.from, currentTask.to);
                continue;
            }
            byte unusedPin = (byte) (6 - currentTask.from - currentTask.to);
            tasks.push(new Task(currentTask.numberOfDiscs - 1, unusedPin, currentTask.to));
            tasks.push(new Task(1, currentTask.from, currentTask.to));
            tasks.push(new Task(currentTask.numberOfDiscs - 1, currentTask.from, unusedPin));
        }
    }


    /**
     * Searches for a best item set that can fit into a given value
     *
     * @param bagSize - maximum total size of items
     * @param items - base item set
     * @return set of items that can fit in size and have maximum possible value
     */
    @SuppressWarnings("unchecked")
    public static List<BagItem> getBestItemChose(int bagSize, List<BagItem> items) {
        int[] lastBest = new int[bagSize + 1];
        List<BagItem>[] lastBestItems = new List[bagSize + 1];
        for (int i = 0; i < bagSize + 1; i++) {
            lastBestItems[i] = new LinkedList<BagItem>();
        }

        for (BagItem item : items) {
            int[] currentBest = new int[bagSize + 1];
            List<BagItem>[] currentBestItems = new List[bagSize + 1];

            for (int i = 0; i < bagSize + 1; i++) {
                if (i < item.getSize()) {
                    currentBest[i] = lastBest[i];
                    currentBestItems[i] = lastBestItems[i];
                    continue;
                }
                int possibleValue = item.getValue() + lastBest[i - item.getSize()];
                if (possibleValue > lastBest[i]) {
                    currentBest[i] = possibleValue;
                    currentBestItems[i] = new LinkedList<BagItem>();
                    currentBestItems[i].add(item);
                    currentBestItems[i].addAll(lastBestItems[i - item.getSize()]);
                    continue;
                }
                currentBest[i] = lastBest[i];
                currentBestItems[i] = lastBestItems[i];
            }

            lastBest = currentBest;
            lastBestItems = currentBestItems;
        }

        return lastBestItems[bagSize];
    }

    @SuppressWarnings("unchecked")
    public static List<BagItem> getBestItemChoiceRecursively(int bagSize, List<BagItem> items) {
        int[] lastBest = new int[bagSize + 1];
        List<BagItem>[] lastBestItems = new List[bagSize + 1];
        for (int i = 0; i < bagSize + 1; i++) {
            lastBestItems[i] = new LinkedList<BagItem>();
        }

        getBestItemChoiceRecursively(bagSize, items, lastBest, lastBestItems);
        return lastBestItems[bagSize];
    }

    @SuppressWarnings("unchecked")
    private static void getBestItemChoiceRecursively(int bagSize, List<BagItem> items, int[] lastBest, List<BagItem>[] lastBestItems) {

        if (items.size() == 1) {

            int[] currentBest = new int[bagSize + 1];
            List<BagItem>[] currentBestItems = new List[bagSize + 1];
            BagItem item = items.get(0);

            for (int i = 0; i < bagSize + 1; i++) {
                if (i < item.getSize()) {
                    currentBest[i] = lastBest[i];
                    currentBestItems[i] = lastBestItems[i];
                    continue;
                }
                int possibleValue = item.getValue() + lastBest[i - item.getSize()];
                if (possibleValue > lastBest[i]) {
                    currentBest[i] = possibleValue;
                    currentBestItems[i] = new LinkedList<BagItem>();
                    currentBestItems[i].add(item);
                    currentBestItems[i].addAll(lastBestItems[i - item.getSize()]);
                    continue;
                }
                currentBest[i] = lastBest[i];
                currentBestItems[i] = lastBestItems[i];
            }

            for (int i = 0; i < bagSize + 1; i++) {
                lastBest[i] = currentBest[i];
                lastBestItems[i] = currentBestItems[i];
            }

            return;
        }

        getBestItemChoiceRecursively(bagSize, items.subList(0, 1), lastBest, lastBestItems);
        getBestItemChoiceRecursively(bagSize, items.subList(1, items.size()), lastBest, lastBestItems);
    }

}
