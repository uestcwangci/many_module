package ali2018.zhengshi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Demo1 {

    private static class Rect {
        private int left;
        private int top;
        private int width;
        private int height;

        public Rect(int left, int top, int width, int height) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        Rect[] rects = new Rect[n];
        for (int i = 0; i < n; i++) {

            rects[i].left = Integer.parseInt(in.nextLine());
            rects[i].top = Integer.parseInt(in.nextLine());
            rects[i].width = Integer.parseInt(in.nextLine());
            rects[i].height = Integer.parseInt(in.nextLine());
        }
        System.out.println(String.valueOf(costTime(rects)));
    }

    private static long costTime(Rect[] rects) {
        long cost = 0;
        for (Rect rect : rects) {
            cost += 10000 + rect.width * rect.height;
        }
        long total = 0;
        for (int i = 0; i < rects.length-1; i++) {
            int left = Math.min(rects[i].left, rects[i + 1].left);
            int top = Math.min(rects[i].top, rects[i + 1].top);
            int height = Math.max(left + rects[i].height, left + rects[i + 1].height);
            int width = Math.max(top + rects[i].width, top + rects[i + 1].width);
            long combine = height * width + 10000;
            cost += 20000 + rects[i].width * rects[i].height + rects[i + 1].width * rects[i + 1].height;
            if (cost > combine) {
                cost = combine;
                rects[i + 1] = new Rect(left, top, width, height);
            }
            total += cost;
        }
        for (Rect rect : rects) {
            cost += 10000 + rect.width * rect.height;
        }
        return total;
    }
}