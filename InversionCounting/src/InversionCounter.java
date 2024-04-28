// Steven Knaack; 577 Spring 2023; InversionCounter.java

import java.util.Scanner;

public class InversionCounter {
  public static int numInversions(int[] sequence, int sIndex, int eIndex) {
    int size = eIndex - sIndex + 1;
    if (size == 1) return 0;
    
    int numInversionsFirstHalf = 
        numInversions(sequence, sIndex, sIndex + size/2 - 1);
    int numInversionsSecondHalf = 
        numInversions(sequence, sIndex + size/2, eIndex);
    
    int numInversions = numInversionsFirstHalf + numInversionsSecondHalf;
    numInversions += merge(sequence, sIndex, sIndex + size/2 - 1, 
        sIndex + size/2, eIndex);
    
    return numInversions;
  }
  
  public static int merge(int[] sequence, int sIndexA, int eIndexA, 
      int sIndexB, int eIndexB) {
  
    int sizeA = eIndexA - sIndexA + 1;
    int sizeB = eIndexB - sIndexB + 1;
    
    int[] merged = new int[sizeA + sizeB];
    int inversions = 0;
    
    int aInd = sIndexA;
    int bInd = sIndexB;
    for (int i = 0; i < merged.length; i++) {
      if (bInd > eIndexB) {
        merged[i] = sequence[aInd];
        aInd++;
      } else if (aInd > eIndexA) {
        merged[i] = sequence[bInd];
        bInd++;
      } else {
        int a = sequence[aInd];
        int b = sequence[bInd];
        
        if (a <= b) {
          merged[i] = sequence[aInd];
          aInd++;
        } else {
          merged[i] = sequence[bInd];
          bInd++;
          inversions += (eIndexA - aInd + 1);
        }
      }
    }
    
    for (int i = 0; i < merged.length; i++) {
      sequence[i + sIndexA] = merged[i];
    }
    
    return inversions;
  }
  
  public static int[] getSequence(Scanner input) {
    int sizeOfSequence = input.nextInt();
    input.nextLine();
    
    int[] sequence = new int[sizeOfSequence];
    
    for (int i = 0; i < sizeOfSequence; i++ ) {
      sequence[i] = input.nextInt();
    }
    
    //input.nextLine();
    return sequence;
  }
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int numInstances = input.nextInt();
    input.nextLine();
    
    for (int i = 0; i < numInstances; i++) {
      int[] sequence = getSequence(input);
      System.out.println(numInversions(sequence, 0, sequence.length - 1));
    }
  }
}
