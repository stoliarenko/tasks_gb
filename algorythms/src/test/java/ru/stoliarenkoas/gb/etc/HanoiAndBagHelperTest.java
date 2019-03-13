package ru.stoliarenkoas.gb.etc;

import org.junit.Assert;
import org.junit.Test;
import ru.stoliarenkoas.gb.etc.model.BagItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

    @Test
    public void bagTest() {
        List<BagItem> inList = new LinkedList<BagItem>();
        inList.add(BagItem.SOCKS);
        inList.add(BagItem.GUITAR);
        inList.add(BagItem.BALL);
        inList.add(BagItem.CANNED_FOOD);
        inList.add(BagItem.MATCHES);

        List<BagItem> outList = TowerOfHanoiHelper.getBestItemChose(1, inList);
        Assert.assertEquals(1, outList.size());
        Assert.assertEquals(outList.get(0), BagItem.MATCHES);

        outList = TowerOfHanoiHelper.getBestItemChose(3, inList);
        Assert.assertEquals(2, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD)));

        outList = TowerOfHanoiHelper.getBestItemChose(5, inList);
        Assert.assertEquals(3, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD, BagItem.SOCKS)));
    }
    
    @Test
    public void bagRecursiveTest() {
        List<BagItem> inList = new LinkedList<BagItem>();
        inList.add(BagItem.SOCKS);
        inList.add(BagItem.GUITAR);
        inList.add(BagItem.BALL);
        inList.add(BagItem.CANNED_FOOD);
        inList.add(BagItem.MATCHES);

        List<BagItem> outList = TowerOfHanoiHelper.getBestItemsRecursively(1, inList);
        Assert.assertEquals(1, outList.size());
        Assert.assertEquals(outList.get(0), BagItem.MATCHES);

        outList = TowerOfHanoiHelper.getBestItemsRecursively(3, inList);
        Assert.assertEquals(2, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD)));

        outList = TowerOfHanoiHelper.getBestItemsRecursively(5, inList);
        Assert.assertEquals(3, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD, BagItem.SOCKS)));
    }

}
