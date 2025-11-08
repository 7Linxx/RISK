package co.edu.unbosque.service;

import java.util.Arrays;
import java.util.Random;

public class CombatService {
    private static final Random RAND = new Random();

    public static class Result {
        public int attackerLosses;
        public int defenderLosses;
    }

    // attDice: 1-3, defDice: 1-2
    public static Result resolveAttack(int attDice, int defDice) {
        int[] attRolls = roll(attDice);
        int[] defRolls = roll(defDice);

        Arrays.sort(attRolls); reverse(attRolls);
        Arrays.sort(defRolls); reverse(defRolls);

        Result r = new Result();
        int comparisons = Math.min(attDice, defDice);
        for (int i = 0; i < comparisons; i++) {
            if (attRolls[i] > defRolls[i]) {
                r.defenderLosses++;
            } else {
                r.attackerLosses++;
            }
        }
        return r;
    }

    private static int[] roll(int n) {
        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
			r[i] = RAND.nextInt(6) + 1;
		}
        return r;
    }

    private static void reverse(int[] arr) {
        for (int i = 0; i < arr.length/2; i++) {
            int t = arr[i]; arr[i] = arr[arr.length-1-i]; arr[arr.length-1-i] = t;
        }
    }
}