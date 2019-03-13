package ru.stoliarenkoas.gb.etc;

public class Recursion {

    /**
     * Method is raising number to the given power
     */
    public static double pow(int number, int power) {
        if(power == 0) return 1;
        if(number == 0) return 0;

        boolean isNegative = power < 0;
        if (isNegative) power = -power;

        double result = isNegative ? 1d / number : number;
        while(power > 1) {
            result = isNegative ? result / number : result * number;
            if (power % 2 == 0) {
                power /= 2;
                number *= number;
            } else {
                power--;
            }
        }
        return result;
    }

    /**
     * Method is raising number to the given power using recursion
     */
    public static double recPow(double number, double power) {
        if(power == 0) return 1;
        if(number == 0) return 0;

        if (power % 2 == 0) return recPow(number*number, power/2);
        return power < 0 ? recPow(number, ++power) / number : recPow(number, --power) * number;
    }

}
