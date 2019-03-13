package ru.stoliarenkoas.gb.etc;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TowerOfHanoiHelperTest {
    
    private final static List<byte[]> n5List = Arrays.asList(new byte[][]{
            {1, 3}, {1, 2}, {3, 2}, {1, 3},
            {2, 1}, {2, 3}, {1, 3}, {1, 2},
            {3, 2}, {3, 1}, {2, 1}, {3, 2},
            {1, 3}, {1, 2}, {3, 2}, {1, 3},
            {2, 1}, {2, 3}, {1, 3}, {2, 1},
            {3, 2}, {3, 1}, {2, 1}, {2, 3},
            {1, 3}, {1, 2}, {3, 2}, {1, 3},
            {2, 1}, {2, 3}, {1, 3}
    });

    @Test
    public void test() {
        List<byte[]> resultList = TowerOfHanoiHelper.getTowerMoveAlgorithm(5);
        Assert.assertEquals(resultList.size(), n5List.size());
        for (int i = 0; i < n5List.size(); i++) {
            Assert.assertTrue(Arrays.equals(n5List.get(i), resultList.get(i)));
        }
    }

}
