package org.nuaa.tomax.mailclient.core;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for(int q = 0; q < t; q++) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int k = scanner.nextInt();
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();

            float preA = ((float) a) / 2f;
            float preB = ((float) b) / 3f;
            if (3 * a == 2 * b) {
                int x1 = n / 3;
                int x2 = m / 3;
                int y1 = 0;
                if (x1 * 3 < n) {
                    y1 = (n - x1 * 3) / 2;
                }
                int y2 = 0;
                if (x2 * 3 < m) {
                    y2 = (m - x2 * 3) / 2;
                }
                int z = 0;
                int last1 = 0;
                int last2 = 0;
                if (x1 * 3 + y1 * 2 < n) {
                    y1++;
                    last1 = (x1 * 3 + y1 * 2 - n);
                }
                if (x2 * 3 + y1 * 2 < m) {
                    y2++;
                    last2 = (x2 * 3 + y2 * 2 - m);
                }
                if (last1 > 0 && last2 > 0) {
                    k--;
                    last1--;
                    last2--;
                }
                if (c <= a) {
                    z = k;
                } else {
                    int lm = k;
                    int lwm = k;
                    lwm -= last2;
                    int lx1 = lm / 3;
                    int lx2 = lwm / 3;
                    int ly1 = 0;
                    if (lx1 * 3 < lm) {
                        ly1 = (lm - lx1 * 3) / 2;
                    }
                    int ly2 = 0;
                    if (lx2 * 3 < lwm) {
                        ly2 = (lwm - lx2 * 3) / 2;
                    }
                    if (lx1 * 3 + ly1 * 2 < lm) {
                        ly1++;
                    }
                    if (lx2 * 3 + ly2 * 2 < lwm) {
                        ly2++;
                    }
                    x1 += lx1;
                    x2 += lx2;
                    y1 += ly1;
                    y2 += ly2;
                }
                System.out.println(((x1 + x2) * a + (y1 + y2) * b + z * c));
            } else if (preA > preB) {
                int y1 = 0;
                int y2 = 0;
                int x1 = n / 3;
                int z = 0;
                int last1 = 0;
                int last2 = 0;
                if (x1 * 3 < n) {
                    if (a > b) {
                        x1++;
                        last1 += x1 * 3 - n;
                    } else {
                        y1++;
                    }
                }
                int x2 = m / 3;
                if (x2 * 3 < m) {
                    if (a > b) {
                        x2++;
                        last2 += x2 * 3 - n;
                    } else {
                        y2++;
                    }
                }
                if (last1 > 0 && last2 > 0) {
                    last1--;
                    last2--;
                    k--;
                }
                if (last1 > 0 && last2 > 0) {
                    last1--;
                    last2--;
                    k--;
                }
                if (c / 2 <= b / 3) {
                    z = k;
                } else {
                    int lm = k;
                    int lwm = k;
                    lm -= last1;
                    lwm -= last2;
                    int lx1 = lm / 3;
                    int lx2 = lwm / 3;
                    int ly1 = 0;
                    if (lx1 * 3 < lm) {
                        if (a > b) {
                            lx1++;
                        } else {
                            ly1++;
                        }
                    }
                    int ly2 = 0;
                    if (lx2 * 3 < lwm) {
                        if (a > b) {
                            lx2++;
                        } else {
                            ly2++;
                        }
                    }
                    x1 += lx1;
                    x2 += lx2;
                    y1 += ly1;
                    y2 += ly2;
                }

                System.out.println(((x1 + x2) * a + (y1 + y2) * b + z * c));
            } else {
                int x1 = n / 2;
                int last1 = 0;
                if (x1 * 2 < n) {
                    x1++;
                    last1++;
                }
                int y1 = m / 2;
                int last2 = 0;
                if (y1 * 2 < m) {
                    y1++;
                    last2++;
                }
                if (last1 > 0 && last2 > 0) {
                    last1--;
                    last2--;
                    k--;
                }
                int z = 0;
                if (c <= a) {
                    z = k;
                } else {
                    int lx1 = k / 2 - last1;
                    if (lx1 * 2 < k) {
                        lx1++;
                    }
                    x1 += lx1;
                    int lx2 = k / 2 - last2;
                    if (lx2 * 2 < k) {
                        lx2++;
                    }
                    y1 += lx2;
                }
                System.out.println(((x1 + y1) * a + z * c));
            }
        }
    }
}
