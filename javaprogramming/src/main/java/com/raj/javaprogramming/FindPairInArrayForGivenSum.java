package com.raj.javaprogramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindPairInArrayForGivenSum {

    public static void main(String[] args) {

//        findArraySumSimple();
//          findArraySumSimple2();
            findArraySumBest();
    }

    private static void findArraySumBest() {

        int[] input = {3,5,1,-4,8,11,4};
        int sum = 7;

        Set set = new HashSet();

        for (int i = 0; i< input.length;i++) {

            int temp = sum - input[i];

            if (set.contains(temp)) {
                System.out.println("Pair is [" + input[i] +"," + temp + "]");
            } else
            set.add(input[i]);
        }


    }

    private static void findArraySumSimple2() {
        int[] input = {3,5,1,-4,8,11,4};
        int sum = 7;

        int start = 0;
        int end = input.length -1;

        Arrays.sort(input);

        while (start < end) {

            if (input[start] + input[end]  < sum) {
                start++;
            } else if (input[start] + input[end]  > sum){
                end --;
            } else if (input[start] + input[end] == sum) {
                System.out.println("Pair is [" + input[start] +"," + input[end] + "]");
                start++;
                end --;
            }
        }


    }

    private static void findArraySumSimple() {
        int[] input = {3,5,1,-4,8,11};
        int sum = 7;

        for (int i = 0; i < input.length; i++) {
            for (int j = i+1; j< input.length; j++) {
                if (input[i] + input[j] == 7) {
                    System.out.println("Pair is [" + input[i] +"," + input[j] + "]");
                }
            }
        }
    }
}
