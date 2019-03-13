package ru.stoliarenkoas.gb.etc;

import java.util.ArrayList;
import java.util.List;


public class TowerOfHanoiHelper {

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

}
