package com.os.process;

import java.util.Comparator;
import java.util.Random;

public class Process 
{
	//A process is either interactive or cpu-bound
	private int type;
	public static final int TYPE_INTERACTIVE = 0;
	public static final int TYPE_CPU = 1;
	public int getType() { return type; }
	public String getTypeString() 
	{
		if (type == TYPE_INTERACTIVE) { return "Interactive"; }
		if (type == TYPE_CPU) { return "CPU-bound"; }
		return ""; 
	}
	
	//id
	public int ID;
	
	//random number generator for bursts
	private Random randomObj;
	
	public Process(int type, int id)
	{
		randomObj = new Random();
		
		this.type = type;
		this.ID = id;
		if (type == TYPE_INTERACTIVE)
		{
			burstTime = randomObj.nextInt(200 - 20) + 20; 
		}
		else if (type == TYPE_CPU)
		{
			//there are b=6 bursts
			burstTime = randomObj.nextInt(3000 - 200) + 200;
			
			burstsLeft = 2; //set to 2 for testing purposes
		}
		
		totalBurst = 0;
		totalWait = 0;
		totalTurnaround = 0;
		lastTimeRan = 0;
	}
	
	//each process has its own burst time
	private int burstTime, totalBurst; 
	public int getBurstTime() { return burstTime; } 
	public int getTotalBurst() { return this.totalBurst; }
	
	
	//between each process, there is a random block of time where the human spends time responding
	public int getHumanResponseTime() { return randomObj.nextInt(4500 - 1000) + 1000; }
	
	//for cpu-bound processes, there are a limited number of bursts.
	//once the bursts run out, the process terminates
	private int burstsLeft;
	public void decrementBursts() { burstsLeft--; } 
	public boolean isBurstsDone() { return (burstsLeft <= 0); } 
		
	//current wait time
	private int lastTimeRan;
	public int getWaitTime(int currentTime) { return currentTime - this.lastTimeRan; }
	public void setLastTimeRan(int time) { this.lastTimeRan = time; }
	
	//process statistics (total wait time, total turnaround time)
	private int totalTurnaround, totalWait;
	public int getTotalWait() { return this.totalWait; }
	public int getTotalTurnaround() { return this.totalTurnaround; }
	public void incrementProcessStats(int burstTime, int waitTime, int turnaroundTime) 
	{ 
		this.totalBurst += burstTime; 
		this.totalWait += waitTime;
		this.totalTurnaround += turnaroundTime; 
	}
	
	public static Comparator<Process> ProcessComparatorBurst = new Comparator<Process>(){
		public int compare(Process p1, Process p2)
		{
			return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
		}
	};
	
	public static Comparator<Process> ProcessComparatorID = new Comparator<Process>(){
		public int compare(Process p1, Process p2)
		{
			return Integer.compare(p1.ID, p2.ID);
		}
	};

}
