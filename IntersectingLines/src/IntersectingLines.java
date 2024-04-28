import java.util.Scanner;

public class IntersectingLines {
  private static class Point {
    int q;
    int p;
  }
  
  public static long numIntersections(Point[] points, int sIndex, int eIndex) {
    int size = eIndex - sIndex + 1;
    if (size == 1) return 0;
    
    long numIntersectionsFirstHalf = 
        numIntersections(points, sIndex, sIndex + size/2 - 1);
    long numIntersectionsSecondHalf = 
        numIntersections(points, sIndex + size/2, eIndex);
    
    long numIntersections = numIntersectionsFirstHalf + numIntersectionsSecondHalf;
    numIntersections += merge(points, sIndex, sIndex + size/2 - 1, 
        sIndex + size/2, eIndex);
    
    return numIntersections;
  }
  
  public static long merge(Point[] points, int sIndexA, int eIndexA, 
      int sIndexB, int eIndexB) {
  
    int sizeA = eIndexA - sIndexA + 1;
    int sizeB = eIndexB - sIndexB + 1;
    
    Point[] merged = new Point[sizeA + sizeB];
    long Intersections = 0;
    
    int aInd = sIndexA;
    int bInd = sIndexB;
    for (int i = 0; i < merged.length; i++) {
      if (bInd > eIndexB) {
        merged[i] = points[aInd];
        aInd++;
      } else if (aInd > eIndexA) {
        merged[i] = points[bInd];
        bInd++;
      } else {
        Point a = points[aInd];
        Point b = points[bInd];
        
        if (a.p <= b.p) {
          merged[i] = points[aInd];
          aInd++;
        } else {
          merged[i] = points[bInd];
          bInd++;
          Intersections += (eIndexA - aInd + 1);
        }
      }
    }
    
    for (int i = 0; i < merged.length; i++) {
      points[i + sIndexA] = merged[i];
    }
    
    return Intersections;
  }
  
  public static void sortByQ(Point[] points, int sIndex, int eIndex) {
    int size = eIndex - sIndex + 1;
    if (size == 1) return;
    
    sortByQ(points, sIndex, sIndex + size/2 - 1);
    sortByQ(points, sIndex + size/2, eIndex);
    
    mergeSortByQ(points, sIndex, sIndex + size/2 - 1, 
        sIndex + size/2, eIndex);
  }
  
  public static void mergeSortByQ(Point[] points, int sIndexA, int eIndexA, 
      int sIndexB, int eIndexB) {
  
    int sizeA = eIndexA - sIndexA + 1;
    int sizeB = eIndexB - sIndexB + 1;
    
    Point[] merged = new Point[sizeA + sizeB];
    
    int aInd = sIndexA;
    int bInd = sIndexB;
    for (int i = 0; i < merged.length; i++) {
      if (bInd > eIndexB) {
        merged[i] = points[aInd];
        aInd++;
      } else if (aInd > eIndexA) {
        merged[i] = points[bInd];
        bInd++;
      } else {
        Point a = points[aInd];
        Point b = points[bInd];
        
        if (a.q <= b.q) {
          merged[i] = points[aInd];
          aInd++;
        } else {
          merged[i] = points[bInd];
          bInd++;
        }
      }
    }
    
    for (int i = 0; i < merged.length; i++) {
      points[i + sIndexA] = merged[i];
    }
  }
  
  public static Point[] getPoints(Scanner input) {
    int sizeOfPoints = input.nextInt();
    input.nextLine();
    
    Point[] points = new Point[sizeOfPoints];
    
    for (int i = 0; i < sizeOfPoints; i++ ) {
      points[i] = new Point();
      points[i].q = input.nextInt();
    }
    
    for (int i = 0; i < sizeOfPoints; i++ ) {
      points[i].p = input.nextInt();
    }
    
    return points;
  }
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int numInstances = input.nextInt();
    input.nextLine();
    
    for (int i = 0; i < numInstances; i++) {
      Point[] points = getPoints(input);
      sortByQ(points, 0, points.length - 1);
      System.out.println(numIntersections(points, 0, points.length - 1));
    }
  }
}
