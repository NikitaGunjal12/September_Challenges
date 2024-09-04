package September_Challenges;
/*
 Problem 1:Convert 1D Array Into 2D Array
 You are given a 0-indexed 1-dimensional (1D) integer array original, and two integers, m and n. You are tasked with creating a 2-dimensional (2D) array with  m rows and n columns using all the elements from original.

The elements from indices 0 to n - 1 (inclusive) of original should form the first row of the constructed 2D array, the elements from indices n to 2 * n - 1 (inclusive) should form the second row of the constructed 2D array, and so on.
Return an m x n 2D array constructed according to the above procedure, or an empty 2D array if it is impossible.

Example 1:

Input: original = [1,2,3,4], m = 2, n = 2
Output: [[1,2],[3,4]]
Explanation: The constructed 2D array should contain 2 rows and 2 columns.
The first group of n=2 elements in original, [1,2], becomes the first row in the constructed 2D array.
The second group of n=2 elements in original, [3,4], becomes the second row in the constructed 2D array.
Solution: 

 class Solution {
    public int[][] construct2DArray(int[] original, int m, int n) {
        if(original.length != m*n){
            return new int [0][0];
        }
        int[][] result = new int [m][n];

        for(int i=0;i<original.length;i++){
            result[i/n][i%n] = original[i];
        }
        return result;
        
    }
}
---------------------------------------------------------------------------------

Problem 2:
Find the Student that will replace the chalk
There are n students in a class numbered from 0 to n - 1. The teacher will give each student a problem starting with the student number 0, then the student number 1, and so on until the teacher reaches the student number n - 1. After that, the teacher will restart the process, starting with the student number 0 again.

You are given a 0-indexed integer array chalk and an integer k. There are initially k pieces of chalk. When the student number i is given a problem to solve, they will use chalk[i] pieces of chalk to solve that problem. However, if the current number of chalk pieces is strictly less than chalk[i], then the student number i will be asked to replace the chalk.

Return the index of the student that will replace the chalk pieces.

 

Example 1:

Input: chalk = [5,1,5], k = 22
Output: 0
Explanation: The students go in turns as follows:
- Student number 0 uses 5 chalk, so k = 17.
- Student number 1 uses 1 chalk, so k = 16.
- Student number 2 uses 5 chalk, so k = 11.
- Student number 0 uses 5 chalk, so k = 6.
- Student number 1 uses 1 chalk, so k = 5.
- Student number 2 uses 5 chalk, so k = 0.
Student number 0 does not have enough chalk, so they will have to replace it.

Solution:
class Solution {
    public int chalkReplacer(int[] chalk, int k) {
        long sum=0;
        for(int i=0;i<chalk.length;i++){
            sum+=chalk[i];
        }
        int remainingChalks = (int)(k% sum);
        for(int i=0;i<chalk.length;i++){
            if(remainingChalks < chalk[i]){
                return i;
            }
            remainingChalks -=chalk[i];
        }
        return -1;
        
    }
}
-----------------------------------------------------------------------------
Problem 3
Sum of digit of  String After Convert
You are given a string s consisting of lowercase English letters, and an integer k.
First, convert s into an integer by replacing each letter with its position in the alphabet (i.e., replace 'a' with 1, 'b' with 2, ..., 'z' with 26). Then, transform the integer by replacing it with the sum of its digits. Repeat the transform operation k times in total.
For example, if s = "zbax" and k = 2, then the resulting integer would be 8 by the following operations:

Convert: "zbax" ➝ "(26)(2)(1)(24)" ➝ "262124" ➝ 262124
Transform #1: 262124 ➝ 2 + 6 + 2 + 1 + 2 + 4 ➝ 17
Transform #2: 17 ➝ 1 + 7 ➝ 8
Return the resulting integer after performing the operations described above.

Example 1:
Input: s = "iiii", k = 1
Output: 36
Explanation: The operations are as follows:
- Convert: "iiii" ➝ "(9)(9)(9)(9)" ➝ "9999" ➝ 9999
- Transform #1: 9999 ➝ 9 + 9 + 9 + 9 ➝ 36
Thus the resulting integer is 36.
Solutions:
class Solution {
    public int getLucky(String s, int k) {
        int sum1 = 0;
        // First transform the string into numeric representation and compute the initial sum
        for (char ch : s.toCharArray()) {
            int num = ch - 'a' + 1; // Converting char to number (a=1, b=2, ..., z=26)
            while (num > 0) {
                sum1 += (num % 10);
                num /= 10;
            }
        }

        // Transform the sum `k` times
        StringBuilder sb = new StringBuilder(String.valueOf(sum1));
        while (k > 1) {  // Start k at 1 because first transformation already happened
            int sum = 0;
            for (int i = 0; i < sb.length(); i++) {
                sum += (sb.charAt(i) - '0');
            }
            sb = new StringBuilder(String.valueOf(sum));
            k--;
        }
        
        return Integer.parseInt(sb.toString());
    }
}

------------------------------------------------------------------------------
Problem:4
Walking robot Simulations
A robot on an infinite XY-plane starts at point (0, 0) facing north. The robot can receive a sequence of these three possible types of commands:

-2: Turn left 90 degrees.
-1: Turn right 90 degrees.
1 <= k <= 9: Move forward k units, one unit at a time.
Some of the grid squares are obstacles. The ith obstacle is at grid point obstacles[i] = (xi, yi). If the robot runs into an obstacle, then it will instead stay in its current location and move on to the next command.
Return the maximum Euclidean distance that the robot ever gets from the origin squared (i.e. if the distance is 5, return 25).

Note:
North means +Y direction.
East means +X direction.
South means -Y direction.
West means -X direction.
There can be obstacle in [0,0].

Example 1:
Input: commands = [4,-1,3], obstacles = []
Output: 25
Explanation: The robot starts at (0, 0):
1. Move north 4 units to (0, 4).
2. Turn right.
3. Move east 3 units to (3, 4).
The furthest point the robot ever gets from the origin is (3, 4), which squared is 32 + 42 = 25 units away.

Solution:
class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        //2,2 -> 2,3 -> 2,4 -> 2,5 -> 2,6
        // 0: North, 1: East, 2: South, 3: West
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; //constant
        int[] curPos = { 0, 0 };
        int res = 0;
        int curDir = 0; 
        HashMap<Integer,HashSet<Integer>> obstacleMap = new HashMap<>(); //O(N)
        for (int[] obstacle : obstacles) { //x,y //O(N)
            if(!obstacleMap.containsKey(obstacle[0])){
                obstacleMap.put(obstacle[0],new HashSet<>());
            }
            obstacleMap.get(obstacle[0]).add(obstacle[1]);
        }

        for (int command : commands) { //K
            if (command == -1) {
                // Turn right
                curDir = (curDir + 1) % 4;
                continue;
            }
            if (command == -2) {
                // Turn left
                curDir = (curDir - 1);
                if(curDir==-1){
                    curDir=3;
                }
                continue;
            }

            // Move forward
            int[] direction = directions[curDir];
            for (int step = 0; step < command; step++) { //9
                int nextX = curPos[0] + direction[0];
                int nextY = curPos[1] + direction[1];
                if(obstacleMap.containsKey(nextX) && obstacleMap.get(nextX).contains(nextY)){
                    break;
                }
                curPos[0] = nextX;
                curPos[1] = nextY;
            }

            res = Math.max(res,curPos[0] * curPos[0] +curPos[1] * curPos[1]);
        }
        return res;
    }
}

*/
