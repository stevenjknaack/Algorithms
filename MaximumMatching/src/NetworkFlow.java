// Steven Knaack; Sp23 CS577; NetworkFlow.java

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Models a Network Flow Graph and calculates max flow
 * using the Ford-Fulkerson method
 * 
 * @author Steven Knaack
 */
public class NetworkFlow {
  /* Internal Classes */
  
  private static class Vertex {
    int data;
    ArrayList<Edge> incoming = new ArrayList<Edge>();
    ArrayList<Edge> outgoing = new ArrayList<Edge>();
    
    Vertex(int data) { this.data = data; }
  }
  
  private static class Edge {
    Vertex start;
    Vertex end;
    int capacity;
    Edge inverse;
    
    Edge(Vertex start, Vertex end, int capacity) {
      this.start = start;
      this.end = end;
      this.capacity = capacity;
    }
  }
  
  
  /* NetworkFlow Data */
  
  private Vertex source;
  int numVertices;
  
  /* NetworkFlow Public Methods */
  
  public NetworkFlow(Vertex source, int numVertices) { 
    this.source = source; 
    this.numVertices = numVertices;
  }
  
  public long FFMaxFlow() {
    ArrayList<Edge> path = getAugmentingPath();
    
    while (path.size() > 0) {
      updateResidualGraph(path);
      
      path = getAugmentingPath();
    }
    
    long maxFlow = 0;
    for (Edge e: source.incoming) {
      maxFlow += e.capacity;
    }
    
    return maxFlow;
  }
  
  /* NetworkFlow Private Methods */
  
  private ArrayList<Edge> getAugmentingPath() { 
    ArrayList<Edge> path = new ArrayList<Edge>();
    boolean[] visited = new boolean[numVertices];
    
    DFSPathRec(source, visited, path);
    
    return path;
  }
  
  private void DFSPathRec(Vertex curr, boolean[] visited, ArrayList<Edge> path) {
    visited[curr.data - 1] = true;
    
    int prevSize = path.size();
    
    for (Edge e: curr.outgoing) {
      if (e.capacity > 0 && !visited[e.end.data - 1]) {
        if (e.end.data == numVertices) {
          path.add(e);
          visited[e.end.data - 1] = true;
          break;
        } else {
          DFSPathRec(e.end, visited, path);
        }
        
        if (path.size() > prevSize) {
          path.add(0, e);
          break;
        }
      }
    }
  }
  
  private int bottleNeck(ArrayList<Edge> path) {
    int bottleNeck = path.get(0).capacity;
    
    Edge curr;
    for (Edge e: path) {
      if (e.capacity < bottleNeck) {
        bottleNeck = e.capacity;
      }
    }
    
    return bottleNeck;
  }
  
  private void updateResidualGraph(ArrayList<Edge> path) {
    int bottleNeck = bottleNeck(path);
    
    for (Edge e: path) {
      e.inverse.capacity += bottleNeck;
      e.capacity -= bottleNeck;
    }
  }
  
  /* Driver Methods */
  
  public static NetworkFlow getNFFromInput(Scanner input) {
    int numNodes = input.nextInt();
    int numEdges = input.nextInt();
    
    Vertex[] vertices = new Vertex[numNodes];
    Edge[][] edges = new Edge[numNodes][numNodes];
    
    int sourceI, endI, capacity;
    Vertex source, end;
    for (int i = 1; i <= numEdges; i++) {
      sourceI = input.nextInt();
      endI = input.nextInt();
      capacity = input.nextInt();
      
      if (endI == 1 || sourceI == numNodes) {
        capacity = 0;
      } 
      
      source = vertices[sourceI - 1];
      if (source == null) {
        source = new Vertex(sourceI);
        vertices[sourceI - 1] = source;
      } 
      
      end = vertices[endI - 1];
      if (end == null) {
        end = new Vertex(endI);
        vertices[endI - 1] = end;
      } 
      
      Edge forward = edges[sourceI - 1][endI - 1];
      if (forward == null) {
        forward = new Edge(source, end, capacity);
        
        source.outgoing.add(forward);
        end.incoming.add(forward);
        
        Edge backward = new Edge(end, source, 0);
        
        end.outgoing.add(backward);
        source.incoming.add(backward);
      
        forward.inverse = backward;
        backward.inverse = forward;
    
        edges[sourceI - 1][endI - 1] = forward;
        edges[endI - 1][sourceI - 1] = backward;
      } else {
        forward.capacity += capacity;
      }
    }
    
    return new NetworkFlow(vertices[0], numNodes);
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int numInstances = input.nextInt();
    input.nextLine();
    
    for (int i = 1; i <= numInstances; i++) {
      NetworkFlow nf = getNFFromInput(input);
      
      System.out.println(nf.FFMaxFlow());
    }
  }
}
