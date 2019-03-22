package ru.stoliarenkoas.gb.etc;

public class Pow {

    /**
     * Method is raising number to the given power
     */
    public static double pow(double number, int power) {
        if(power == 0) return 1;
        if(number == 0) return 0;

        boolean isNegative = power < 0;
        if (isNegative) power = -power;

        double result = 1;
        while(power > 0) {
            if (power % 2 != 0) result = isNegative ? result / number : result * number;
            power = power >> 1;
            number *= number;
        }
        return result;
    }

    /**
     * Method is raising number to the given power using recursion
     */
    public static double recPow(double number, int power) {
        if(power == 0) return 1;
        if(number == 0) return 0;

        if (power % 2 == 0) return recPow(number*number, power/2);
        return power < 0 ? recPow(number, ++power) / number : recPow(number, --power) * number;
    }

}
