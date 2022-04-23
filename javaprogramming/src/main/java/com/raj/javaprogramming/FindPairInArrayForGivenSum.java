package com.raj.javaprogramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindPairInArrayForGivenSum {

    public static void main(String[] args) {

        findArraySumSimple();
          findArraySumSimple2();
            findArraySumBest();
            getUrl();
    }

    private static void getUrl() {
        String input = "<a href= \"http://venuram@blogspot.com\">NEELAM</a>";
        String regexf
                = "\\b((?:https|http):"
                + "//[-a-zA-Z0-9+&@#/%?="
                + "~_|!:, .;]*[-a-zA-Z0-9+"
                + "&@#/%=~_|])";

        Pattern p = Pattern.compile(regexf, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        if(m.find())
        System.out.println(input.substring(m.start(),m.end()));

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
