package ru.stoliarenkoas.gb.structures;

import java.util.Arrays;
import java.util.LinkedList;

public class MyGraph {
    private LinkedList<Integer>[] adjEdjes;
    private int edgeCounter;

    @SuppressWarnings("unchecked")
    public MyGraph(int size) {
        if (size < 0) size = 0;

        adjEdjes = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            adjEdjes[i] = new LinkedList<Integer>();
        }
    }

    public int setEdgeCount() {
        return edgeCounter;
    }

    public int getGraphSie() {
        return adjEdjes.length;
    }

    private boolean isOutOfBounds(int value) {
        return  value < 0 || value >= adjEdjes.length;
    }

    public void addEdge(int e1, int e2){
        if (isOutOfBounds(e1) || isOutOfBounds(e2)) return;

        adjEdjes[e1].add(e2);
        adjEdjes[e2].add(e1);
        edgeCounter++;
    }

    public void removeEdge(int e1, int e2) {
        if (isOutOfBounds(e1) || isOutOfBounds(e2)) return;

        adjEdjes[e1].remove(Integer.valueOf(e2));
        adjEdjes[e2].remove(Integer.valueOf(e1));
        edgeCounter--;
    }

    public LinkedList<Integer> searchShortWay(int from, int to) {
        //result list and working que
        LinkedList<Integer> result = new LinkedList<Integer>();
        MyQueue<Integer> assetsToCheck = new MyQueue<Integer>();

        //markers
        boolean[] isChecked = new boolean[getGraphSie()];
        int[] parents = new int[getGraphSie()];
        Arrays.fill(parents, -1);

        //initial value
        assetsToCheck.push(from);
        isChecked[from] = true;

        //main loop
        while (assetsToCheck.getSize() > 0) {
            Integer asset = assetsToCheck.pop();
            if (adjEdjes[asset].contains(to)) {
                parents[to] = asset;
                break;
            }

            for (Integer subAsset : adjEdjes[asset]) {
                if (!isChecked[subAsset]) {
                    isChecked[subAsset] = true;
                    parents[subAsset] = asset;
                    assetsToCheck.push(subAsset);
                }
            }
        }

        if (parents[to] != -1) {
            while (to != -1) {
                result.addFirst(to);
                to = parents[to];
            }
        }

        return result;
    }
}
