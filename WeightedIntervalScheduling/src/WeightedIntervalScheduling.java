import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class WeightedIntervalScheduling {
  private static class Job implements Comparable<Job> {
    int startTime;
    int finishTime;
    int weight;
    
    Job(int startTime, int finishTime, int weight) {
      this.startTime = startTime;
      this.finishTime = finishTime;
      this.weight = weight;
    }
    
    @Override
    public int compareTo(WeightedIntervalScheduling.Job o) {
      return finishTime - o.finishTime;
    }
  }

  public static ArrayList<Job> getJobListFromInput(Scanner input) {
    int numJobs = input.nextInt();
    
    ArrayList<Job> jobs = new ArrayList<Job>(numJobs);
    
    int startTime, finishTime, weight;
    for (int i = 0; i < numJobs; i++) {
      startTime = input.nextInt();
      finishTime = input.nextInt();
      weight = input.nextInt();
      
      jobs.add(new Job(startTime, finishTime, weight));
    }
    
    //input.nextLine();
    
    return jobs;
  }
  
  public static long getWeightOfMaxSchedule(ArrayList<Job> jobs) {
    // preprocess by sorting jobs
    jobs.sort(null);
    
    // create the bellman matrix and fill in base case
    long[] maxWeightAt = new long[jobs.size()];
    maxWeightAt[0] = jobs.get(0).weight;
    
    // populate matrix
    long weightIfIn, weightIfNotIn;
    int indexOfFCJ;
    Job currentJob;
    for (int i = 1; i < jobs.size(); i++) {
      currentJob = jobs.get(i);
      
      // get weight if not in solution
      weightIfNotIn = maxWeightAt[i - 1];
      
      // find largest job that is compatible
      indexOfFCJ = -1;
      for (int j = i - 1; j >= 0; j--) {
        if (jobs.get(i).finishTime <= currentJob.startTime) {
          indexOfFCJ = j;
          break;
        }
      }
      
      // get the weight if in the solution
      if (indexOfFCJ == -1) {
        weightIfIn = jobs.get(i).weight;
      } else {
        weightIfIn = maxWeightAt[indexOfFCJ] + jobs.get(i).weight;
      }
      
      //weightIfIn = maxWeightAt[indexOfLastCompatible(jobs, i)] + jobs.get(i).weight;
      maxWeightAt[i] = Math.max(weightIfNotIn, weightIfIn);
    }
    
    // retrieve and return solution
    return maxWeightAt[jobs.size() - 1];
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    int numInstances = input.nextInt();
    input.nextLine();
    
    ArrayList<Job> jobs;
    for (int i = 0; i < numInstances; i++) {
      jobs = getJobListFromInput(input);
      System.out.println(getWeightOfMaxSchedule(jobs));
    }
  }
}
