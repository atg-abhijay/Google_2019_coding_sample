// you can also use imports, for example:
import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Houses {
    public int[] solution(int[] stores, int[] houses) {
        // write your code in Java SE 8
        int numHouses = houses.length;
        int numStores = stores.length;
        int[] closestStores = new int[numHouses];

        for(int i = 0; i < numHouses; i++) {
            int minDistance = (int) Math.pow(10, 8);
            // arbitrary value > 1000
            int closestIndex = 1500;
            for(int j = 0; j < numStores; j++) {
                int diff = Math.abs(houses[i] - stores[j]);
                if(minDistance > diff) {
                    minDistance = diff;
                    closestIndex = j;
                }
                else if(minDistance == diff) {
                    if(j < closestIndex) {
                        closestIndex = j;
                    }
                }
            }
            closestStores[i] = stores[closestIndex];
        }

        return closestStores;
    }
}