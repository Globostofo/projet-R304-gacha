package fr.the_gacha_company.projet_r304_gacha;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Global attributes and methods that can be accessed everywhere
 */
public final class Global {

    public static final String SEPARATOR = "_".repeat(60);

    private  static final Scanner in = new Scanner(System.in);
    private  static final Random rand = new Random();

    /**
     * Gets an int from user.
     * @param msg a String representing displayed instructions for user
     * @return an int representing the first correct user input
     */
    public static String getInput(String msg) {
        System.out.print(msg);
        String s = in.nextLine();
        System.out.println(SEPARATOR);
        return s;
    }

    /**
     * Waits until user finish an input
     */
    public static void pressEnter() {
        System.out.print("\nAppuyez sur <entrée> pour continuer");
        in.nextLine();
        System.out.println(SEPARATOR);
    }

    public static String leftPad(String s, int size) {
        return String.format("%-" + size + "s", s);
    }

    public static String leftPad(int i, int size) {
        return leftPad(String.valueOf(i), size);
    }

    public static String rightPad(String s, int size) {
        return String.format("%" + size + "s", s);
    }

    public static String rightPad(int i, int size) {
        return rightPad(String.valueOf(i), size);
    }

    /**
     * Centers a String in a size chars spaces
     * @param s a String to center
     * @param size an int representing the total requested size
     * @return a String representing the string given center in a string of the given size
     */
    public static String center(String s, int size) {
        int spaceSum = Math.max(size - s.length(), 0);
        return " ".repeat(spaceSum / 2) + s + " ".repeat(spaceSum / 2 + Math.floorMod(spaceSum, 2));
    }

    public static String center(int i, int size) {
        return center(String.valueOf(i), size);
    }

}