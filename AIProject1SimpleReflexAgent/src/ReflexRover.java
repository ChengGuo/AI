import java.io.*;
import java.lang.*;

/**
 * ReflexRover: A class for using RoverSampleSensor to generate series of actions
 * and presenting them one at a time
 *
 * @author Cheng Guo
 * @version 1.0
 *
 */

public class ReflexRover {
	private RoverSampleSensor rss=null;	
	
	public ReflexRover(String FileName){		
		this.rss=new RoverSampleSensor(FileName);

	}
	
	public ReflexRover(){		
		this.rss=new RoverSampleSensor();

	}
	//will keep running untill there is no more input
	public void StartAgent(){
		SamplePercept sp;	
		while((sp=this.rss.getPercept())!=null){
			char action= SimpleReflexAgent(sp.value());
			System.out.println("Percept: "+sp.value()+"\t"+"Action: "+action);
		}
	}
	
	public char SimpleReflexAgent(int value){
		if(value%5==0){
			return 'G';
		}else{
			return 'N';
		}				
	}
	
	
	
    public static void main(String args[]) {
	if (args.length!= 0 && 
	    (args.length != 2 ||   (! args[0].equals("-file")))) {
	    System.err.println("Usage: ReflexRover -file <filename>");
	    System.exit(1);
	}
	ReflexRover rr=null;

	if (args.length==0) {
		rr=new ReflexRover();
	} else {
		rr=new ReflexRover(args[1]);
	}
	
	rr.StartAgent();
	
    }
}
