// Steven Knaack; CS577 Spring 2023; Knapsack.java

import java.util.Scanner;
import java.lang.Math;

public class Knapsack {
  private static class Item {
    int weight;
    int value;
    
    Item(int weight, int value) {
      this.weight = weight;
      this.value = value;
    }
  }
  
  public static Item[] getItemsFromInput(Scanner input, int numItems) {
    Item[] items = new Item[numItems];
    
    int weight, value;
    for (int i = 0; i < numItems; i++) {
      weight = input.nextInt();
      value = input.nextInt();
      
      items[i] = new Item(weight, value);
    }
    
    return items;
  }
  
  public static long getMaxValue(Item[] items, int capacity) {
    // instantiate dynamic matrix
    long[][] maxValues = new long[items.length + 1][capacity + 1];
    
    for (int i = 0; i <= items.length; i++) {
      for (int w = 0; w <= capacity; w++) {
        if (i == 0 || w == 0) {
          maxValues[i][w] = 0;
          continue;
        } 
        
        Item item = items[i - 1];
        
        if (item.weight > w) {
          maxValues[i][w] = maxValues[i - 1][w];
        } else {
          maxValues[i][w] = Math.max(maxValues[i - 1][w], 
              item.value + maxValues[i - 1][w - item.weight]);
        }
      }
    }
    
    // retrieve and return solution
    return maxValues[items.length][capacity];
  }
  
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int numInstances = input.nextInt();
    input.nextLine();
    
    int numItems, capacity;
    Item[] items;
    for (int i = 0; i < numInstances; i++) {
      numItems = input.nextInt();
      capacity = input.nextInt();
      items = getItemsFromInput(input, numItems);
      
      System.out.println(getMaxValue(items, capacity));
    }
  }
}
