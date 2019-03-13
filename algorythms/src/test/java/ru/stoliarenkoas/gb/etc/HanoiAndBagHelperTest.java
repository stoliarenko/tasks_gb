package ru.stoliarenkoas.gb.etc;

import org.junit.Assert;
import org.junit.Test;
import ru.stoliarenkoas.gb.etc.model.BagItem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HanoiAndBagHelperTest {
    
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
    public void towerTest() {
        List<byte[]> resultList = HanoiAndBagHelper.getTowerMoveAlgorithm(5);
        Assert.assertEquals(resultList.size(), n5List.size());
        for (int i = 0; i < n5List.size(); i++) {
            Assert.assertTrue(Arrays.equals(n5List.get(i), resultList.get(i)));
        }
    }

    @Test
    public void towerRecursiveTest() {
        for (int i = 1; i <= 10; i++) {
            List<byte[]> recursiveList = HanoiAndBagHelper.getTowerMoveAlgorithmRecursively(i);
            List<byte[]> nonRecursiveList = HanoiAndBagHelper.getTowerMoveAlgorithm(i);
            for (int j = 0; j < recursiveList.size(); j++) {
                Assert.assertTrue(Arrays.equals(nonRecursiveList.get(j), recursiveList.get(j)));
            }
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

        List<BagItem> outList = HanoiAndBagHelper.getBestItemChose(1, inList);
        Assert.assertEquals(1, outList.size());
        Assert.assertEquals(outList.get(0), BagItem.MATCHES);

        outList = HanoiAndBagHelper.getBestItemChose(3, inList);
        Assert.assertEquals(2, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD)));

        outList = HanoiAndBagHelper.getBestItemChose(5, inList);
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

        List<BagItem> outList = HanoiAndBagHelper.getBestItemChoiceRecursively(1, inList);
        Assert.assertEquals(1, outList.size());
        Assert.assertEquals(outList.get(0), BagItem.MATCHES);

        outList = HanoiAndBagHelper.getBestItemChoiceRecursively(3, inList);
        Assert.assertEquals(2, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD)));

        outList = HanoiAndBagHelper.getBestItemChoiceRecursively(5, inList);
        Assert.assertEquals(3, outList.size());
        Assert.assertTrue(outList.containsAll(Arrays.asList(BagItem.MATCHES, BagItem.CANNED_FOOD, BagItem.SOCKS)));
    }

}
