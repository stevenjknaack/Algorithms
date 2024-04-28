// Steven Knaack; Spring2023; Cache.java; CS577

import java.util.Scanner;

public class Cache {
  private int[] pages;
  
  Cache(int numPages) {
    pages = new int[numPages];
  }
  
  public int indexOf(int n) {
    for (int i = 0; i < pages.length; i++) {
      if (pages[i] == n) {
        return i;
      }
    }
    return -1;
  }
  
  static int[] getRequestsFromInput(Scanner input) {
    int numRequests = input.nextInt();
    input.nextLine();
    
    int[] requests = new int[numRequests];
    
    for (int i = 0; i < numRequests; i++) {
      requests[i] = input.nextInt();
    }
    
    input.nextLine();
    
    return requests;
  }
  
  public int furthestInTheFutureNumPageFaults(int[] requests) {
    int numPageFaults = 0;
    
    for (int i = 0; i < requests.length; i++) {
      if (numPageFaults < pages.length) { // if cache is cold
         pages[numPageFaults] = requests[i];
         numPageFaults++;
       } else if (indexOf(requests[i]) == -1) { // if cache miss
         numPageFaults++;
         int toEvictIndex = farthestInTheFutureIndexToEvict(requests, i);
         pages[toEvictIndex] = requests[i];
       }
    }
    
    return numPageFaults;
  }
  
  public int farthestInTheFutureIndexToEvict(int[] requests, int currRequestIndex) {
    int[] lastOccurence = new int[pages.length];
    
    for (int i = currRequestIndex + 1; i < requests.length; i++) {
      for (int j = 0; j < pages.length; j++) {
        if (requests[i] == pages[j]) {
          lastOccurence[j] = i;
          break;
        }
      }
    }
    
    int farthestInTheFutureIndex = 0;
    
    for (int i = 0; i < lastOccurence.length; i++) {
      if (lastOccurence[i] > farthestInTheFutureIndex) {
        farthestInTheFutureIndex = lastOccurence[i];
      }
    }
    
    // replace the first page if none of the pages appear in the future 
    if (farthestInTheFutureIndex == 0) return 0;
    
    return indexOf(requests[farthestInTheFutureIndex]);
  }
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    // get numInstances
    int numInstances = input.nextInt();
    input.nextLine();
    
    // for each instance
    Cache currCache;
    int[] requests;
    int numPageFaults;
    for (int i = 1; i <= numInstances; i++) {
      // set up cache
      currCache = new Cache(input.nextInt());
      input.nextLine();
      
      // get requests 
      requests = getRequestsFromInput(input);
      
      
      // find/print number of page faults
      numPageFaults = currCache.furthestInTheFutureNumPageFaults(requests);
      System.out.println(numPageFaults - 2);
    }
  }
  
}
