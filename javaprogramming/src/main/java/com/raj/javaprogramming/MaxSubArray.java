package com.raj.javaprogramming;

public class MaxSubArray {

    public static void main(String[] args) {
//        Scanner keyboard = new Scanner(System.in);
//        int n = keyboard.nextInt();
//        int[] nums = new int[n];
//        for(int i = 0; i < n; i++) {
//            nums[i] = keyboard.nextInt();
//        }
        int [] nums = {-2,1,-3,4,-1,2,1,-5,4};

        int[] indices = findMaxSubarrayIndices(nums);
        int sum = 0;
        System.out.print("Max contiguous subarray: ");
        for(int i = indices[0]; i <= indices[1]; i++) {
            System.out.print(nums[i] + " ");
            sum += nums[i];
        }
        System.out.println();
        System.out.println("Max contiguous subarray sum: " + sum);
    }

    private static int[] findMaxSubarrayIndices(int[] nums) {
        int n = nums.length;
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        int currentStart = 0, maxStart = 0, maxEnd = 0;

        for(int i = 1; i < n; i++) {
            if(maxEndingHere + nums[i] < nums[i]) {
                maxEndingHere = nums[i];
                currentStart = i;
            } else {
                maxEndingHere = maxEndingHere + nums[i];
            }

            if(maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                maxStart = currentStart;
                maxEnd = i;
            }
        }

        return new int[]{maxStart, maxEnd};
    }
}
