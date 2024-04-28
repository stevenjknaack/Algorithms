// Steven Knaack; Sp23 CS577; MaximumMatching.java

import java.util.Scanner;

public class MaximumMatching {
  
  private static long maxMatch(String graphString) {
    Scanner strInput = new Scanner(graphString);
    NetworkFlow nf = NetworkFlow.getNFFromInput(strInput);
 
    return nf.FFMaxFlow();
  }
  
  private static String getMaxMatchFromInput(Scanner input) {
    int numA = input.nextInt();
    int numB = input.nextInt();
    int numBiEdges = input.nextInt();
    int numNodes = numA + numB + 2;
    int numEdges = numBiEdges + numA + numB;
    
    String graphString = numNodes + " " + numEdges + "\n";
    
    for (int i = 2; i <= numA + 1; i++) {
      graphString += "1 " + i + " 1\n";
    }
    
    for (int i = numA + 2; i < numNodes; i++) {
      graphString += i + " " + numNodes + " 1\n"; 
    }
    
    int aI, bI;
    for (int i = 1; i <= numBiEdges; i++) {
      aI = input.nextInt();
      bI = input.nextInt();
      
      graphString += (aI + 1) + " " + (bI + numA + 1) + " 1\n";
    }
   
    long maxMatches = maxMatch(graphString);
    
    String perfectMatching = (numA == numB && maxMatches == numA) ? "Y" : "N";
    
    return maxMatches + " " + perfectMatching;
  }
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int numInstances = input.nextInt();
    input.nextLine();
    
    for (int i = 1; i <= numInstances; i++)  {
      System.out.println(getMaxMatchFromInput(input));
    }
  }
}
