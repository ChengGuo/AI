import java.io.*;
import java.lang.*;
import java.util.*;
/**
 * StateRover: Rover agent with states and driven by a main method for testing
 * Usage: java StateRover -file <FILENAME> (e.g.) java StateRover -file ../TestInput/Test2.txt(Test2.txt was hw1-data2.txt copied from lab1 folder)
 * 
 *
 * @author Cheng Guo
 * @version 1.0 
 *
 */
public class StateRover {	
	
	private MovingRoverSensors mrs=null;
	private RoverState rs=null;

	//calculated according to the input map file and use mapsize*5 as the MAXMOVELIMITE to terminate the agent 
	private int mapsize;

	//Constructor with filename
	public StateRover(String FileName){		
		mrs=new MovingRoverSensors(FileName);
		rs=new RoverState(mrs);
		VisionPercept vp=null;
		int p=1;
		vp=mrs.getVisionPercept(1, 1);
		//Calculate the map size
		while(vp!=null){
			p++;
			vp=mrs.getVisionPercept(1, p);
		}
		mapsize=p*2;
		
	}
	//Constructor without file name
	public StateRover(){		
		mrs=new MovingRoverSensors();
		rs=new RoverState(mrs);
		VisionPercept vp=null;
		int p=1;
		vp=mrs.getVisionPercept(1, 1);
		while(vp!=null){
			p++;
			vp=mrs.getVisionPercept(1, p);
		}
		mapsize=p*2;
	}
	
	
	public void ReflexAgentWithState(){
		String action= new String();
		if (!rs.checkGrab()){  			//If the sample not been grabbed yet, grab the sample and update the grabbed flag
			action="GRAB";
			System.out.println("Position: <"+rs.curPosx+","+rs.curPosy+"> Looking: "+rs.getVisionDirection()+" Perceived: <"+rs.getSamplePercept()+
					","+rs.getVisionPerceptString()+"> Action: "+action);
			rs.updateGrab();

		}else if(rs.getVisionPercept()==null){		//looking outside of the given map
			action="LOOK"+rs.getVisionDirection();
			System.out.println("Position: <"+rs.curPosx+","+rs.curPosy+"> Looking: "+rs.getVisionDirection()+" Perceived: <"+0+
					","+rs.getVisionPerceptString()+"> Action: "+action);
			rs.updateVisionDirection();
			
		}else if(!rs.getVisionPercept().isClear()){ //looking inside of the map but with a BOULDER
			action="LOOK"+rs.getVisionDirection();
			System.out.println("Position: <"+rs.curPosx+","+rs.curPosy+"> Looking: "+rs.getVisionDirection()+" Perceived: <"+rs.getSamplePercept()+
					","+rs.getVisionPerceptString()+"> Action: "+action);
			rs.updateVisionDirection();

		}else if (rs.getVisionPercept().isClear()){ //looking inside of the map but clear to move
			action="GO"+rs.getVisionDirection();
			System.out.println("Position: <"+rs.curPosx+","+rs.curPosy+"> Looking: "+rs.getVisionDirection()+" Perceived: <"+rs.getSamplePercept()+
					","+rs.getVisionPerceptString()+"> Action: "+action);
			rs.move();

		}
		
		
		
	}
	// main for testing
	public static void main(String args[]){
		if (args.length!=0 && 
			    (args.length != 2 || (! args[0].equals("-file")))) {
			    System.err.println("Usage: StateRover -file <filename>");
			    System.exit(1);
			}

		StateRover sr=null;
		if (args.length==0) {
		    sr=new StateRover();
		} else {
		    sr=new StateRover(args[1]);
		}
		
		//agent will be terminated after run time exceed MAXMOVELIMIT
		int MAXMOVELIMIT=sr.mapsize*5;
		for (int i=0;i<MAXMOVELIMIT;i++){
			sr.ReflexAgentWithState();
		}
		System.out.println("Total Compounds Collected: "+ sr.rs.sumGrab+" Total Moves: "+ sr.rs.sumMove);
		
	}
	
	
}

