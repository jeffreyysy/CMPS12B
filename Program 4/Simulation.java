//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//February 23, 2018
//simulates the time to complete m number of jobs

import java.io.*;
import java.util.Scanner;

public class Simulation {
  public static Job getJob(Scanner in) {
    String[] s = in.nextLine().split(" ");
    int a = Integer.parseInt(s[0]);
    int d = Integer.parseInt(s[1]);
    return new Job(a, d);
  }

  public static void main(String[] args) throws IOException {
    //1.  check command line arguments
    if(args.length != 1){
       System.err.println("Usage: Simulation file");
       System.exit(1);
    }

    //2.  open files for reading and writing
    Scanner input  = new Scanner(new File(args[0]));
    PrintWriter report = new PrintWriter(new FileWriter(args[0]+".rpt"));
    PrintWriter trace = new PrintWriter(new FileWriter(args[0]+".trc"));

    //3.  read in m jobs from input file
    int numberOfJobs = Integer.parseInt(input.nextLine());
    Job listOfJobs[] = new Job[numberOfJobs];
    for( int i=0; i<numberOfJobs; i++){
      listOfJobs[i] = getJob(input);
    }
    Queue aQueue = new Queue();
    for( int i=0; i<numberOfJobs; i++){
      aQueue.enqueue(listOfJobs[i]);
    }

    report.println("Report file: " + (args[0] + ".rpt"));
    report.println(numberOfJobs + " Jobs:");
    report.println(aQueue.toString());
    report.println();
    report.println("***********************************************************");

    trace.println("Trace file: " + (args[0] + ".trc"));
    trace.println(numberOfJobs + " Jobs:");
    trace.println(aQueue.toString());
    trace.println();

    // 4.  run simulation with n processors for n=1 to n=m-1  {
    for( int i=1; i<numberOfJobs;i++){
      trace.println("*****************************");
      if(i == 1) {
        trace.println(i + " processor:");
      }
      else {
        trace.println(i + " processors:");
      }
      trace.println("*****************************");

      //5.      declare and initialize an array of n processor Queues and any necessary storage Queues
      Queue storageQueue = new Queue();
      Queue finishQueue = new Queue();
      Queue processors[] = new Queue[i];
      for (int j=0;j<i;j++) {
        processors[j] = new Queue();
      }
      for(int j=0; j<numberOfJobs; j++){
        storageQueue.enqueue(listOfJobs[j]);
      }
      //6.      while unprocessed jobs remain  {
      int time = 0;
      trace.println("time=" +time);
      trace.println("0: "+storageQueue.toString()+finishQueue.toString());
      for(int j=0;j<i;j++){
        trace.println((j+1)+": "+processors[j].toString());
      }
      trace.println();
      while(finishQueue.length()<numberOfJobs){
        //7.          determine the time of the next arrival or finish event and update time
        if(!storageQueue.isEmpty()){
          time = ((Job) storageQueue.peek()).getArrival();
        }
        else {
          time = Integer.MAX_VALUE;
        }
        for( int j=0;j<i;j++){
          if(!processors[j].isEmpty()){
            time = Math.min(((Job)processors[j].peek()).getFinish(),time);
          }
        }
        //8.          complete all processes finishing now
        for( int j=0;j<i;j++){
          while(!processors[j].isEmpty()){
            if(((Job)processors[j].peek()).getFinish()>time){
              break;
            }
            finishQueue.enqueue(processors[j].dequeue());
            if(processors[j].length()>0){
              ((Job)processors[j].peek()).computeFinishTime(time);
            }
          }
        }
        //9.          if there are any jobs arriving now, assign them to a processor Queue of minimum length and with lowest index in the queue array.
        while((!storageQueue.isEmpty())&&((Job)storageQueue.peek()).getArrival()<= time){
          int minLength = processors[0].length();
          int index = 0;
          for(int j=1;j<i;j++){
            if(processors[j].length()<minLength){
              minLength = processors[j].length();
              index = j;
            }
          }
          if(minLength==0){
            ((Job)storageQueue.peek()).computeFinishTime(time);
          }
          processors[index].enqueue(storageQueue.dequeue());
        }
        trace.println("time=" +time);
        trace.println("0: "+storageQueue.toString()+finishQueue.toString());
        for(int j=0;j<i;j++){
          trace.println((j+1)+": "+processors[j].toString());
        }
        trace.println();
      }
      // 10.     } end loop
      // 11.     compute the total wait, maximum wait, and average wait for all Jobs, then reset finish times
      int totalWait = 0;
      int maxWait = 0;
      while(!finishQueue.isEmpty()){
        Job temp = (Job)finishQueue.dequeue();
        int wait = temp.getWaitTime();
        temp.resetFinishTime();
        totalWait+=wait;
        maxWait = Math.max(maxWait,wait);
      }
      if(i==1) {
        report.println(""+i+" processor: totalWait=" + totalWait +", maxWait=" + maxWait +", averageWait=" + String.format( "%.2f",((double)totalWait/numberOfJobs)));
      }
      else {
        report.println(""+i+" processors: totalWait=" + totalWait +", maxWait=" + maxWait +", averageWait=" + String.format( "%.2f",((double)totalWait/numberOfJobs)));
      }
    }
    //12. } end loop
    //13. close input and output files
    report.close();
    trace.close();
    input.close();


  }
}
