package org.nuaa.tomax.mailclient.utils;

/**
 * @Name: Main
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-13 20:45
 * @Version: 1.0
 */
import java.util.Scanner;
public class Main {
    public static int v(int x, int bi, int ai, int ci) {
        return Math.min(x, bi) - ai + ci;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for(int q = 0; q < t; q++) {
            int n = scanner.nextInt();
            int c = scanner.nextInt();
            int[] a = new int[n + 1];
            int[] b = new int[n + 1];
            int[] x = new int[n + 1];

            for(int i = 1; i <= n; i++) {
                a[i] = scanner.nextInt();
                b[i] = scanner.nextInt();
                x[i] = scanner.nextInt();
            }
        }
    }
}
