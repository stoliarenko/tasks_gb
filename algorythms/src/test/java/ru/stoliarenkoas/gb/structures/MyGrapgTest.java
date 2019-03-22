package ru.stoliarenkoas.gb.structures;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class MyGrapgTest {

    @Test
    public void testShortWaySearch() {
        MyGraph graph = getGraph();
        LinkedList<Integer> shortWay = graph.searchShortWay(1, 0);
        int[] realShortWay = {1, 5, 8, 9, 0};
        Assert.assertEquals(realShortWay.length, shortWay.size());
        for (int i = 0; i < shortWay.size(); i++) {
            Assert.assertEquals(Integer.valueOf(realShortWay[i]), shortWay.get(i));
        }

        shortWay = graph.searchShortWay(0, 2);
        realShortWay = new int[]{0, 9, 8, 5, 1, 2};
        Assert.assertEquals(realShortWay.length, shortWay.size());
        for (int i = 0; i < shortWay.size(); i++) {
            Assert.assertEquals(Integer.valueOf(realShortWay[i]), shortWay.get(i));
        }
    }

    private static MyGraph getGraph() {
        MyGraph graph = new MyGraph(10);
        graph.addEdge(1, 2);
        graph.addEdge(1, 5);
        graph.addEdge(4, 5);
        graph.addEdge(4, 8);
        graph.addEdge(5, 8);
        graph.addEdge(6, 3);
        graph.addEdge(6, 7);
        graph.addEdge(9, 8);
        graph.addEdge(9, 0);

        return graph;
    }
}
