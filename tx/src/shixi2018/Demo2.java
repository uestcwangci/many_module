package shixi2018;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Demo2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String[] s = sc.nextLine().split(" ");
        int[] iArray = new int[n];
        for (int i = 0; i < s.length; i++) {
            iArray[i] = Integer.parseInt(s[i]);
        }
        System.out.println(pooker(n, iArray));
    }

    public static int pooker(int n, int[] a) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < a.length; i++) {
            priorityQueue.add(a[i]);
        }
        int result = 0;
        if (n % 2 == 0) {
            while (!priorityQueue.isEmpty()) {
                result -= priorityQueue.poll();
                result += priorityQueue.poll();
            }
        } else {

            while (priorityQueue.size() > 1) {
                result = priorityQueue.poll() - priorityQueue.poll();
            }
            result += priorityQueue.poll();
        }
        return result;
    }
}
