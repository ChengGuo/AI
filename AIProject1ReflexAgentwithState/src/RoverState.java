import java.util.Random;
/**
 * RoverState: Rover State class used by Rover agent to easier determine current state to planing next move accordingly
 * 
 * @author Cheng Guo
 * @version 1.0 
 *
 */


public class RoverState {	
	public int curPosx;	//current position x component
	public int curPosy; //current position y component
	public int sumGrab; //sum of all grabbing actions till current state
	public int sumMove; //sum of all moving action till current state
	public int visionDirection; //0 for North, 1 for East, 2 for South and 3 for West
	public MovingRoverSensors mrs;
	
	
	//Constructor with input of MovingRoverSensor
	public RoverState(MovingRoverSensors in){
		curPosx=1;
		curPosy=1;
		sumGrab=0;
		sumMove=0;
		visionDirection=0;
		mrs=in;
				
	}
	
	//Getting Vision Direction in String
	public String getVisionDirection(){
		String dir = new String();
		switch (visionDirection){
		case 0: dir= "NORTH"; break;  //Looking North
		case 1: dir= "EAST"; break;  //Looking East
		case 2: dir= "SOUTH";break; //Looking South
		case 3: dir= "WEST";break; //Looking West
	}
	return dir;
		
	}
	
	//Getting VisionPercept object
	public VisionPercept getVisionPercept(){
		VisionPercept out = new VisionPercept("clear");
		switch (visionDirection){
			case 0: out= mrs.getVisionPercept(curPosx, curPosy+1); break;  //Looking North
			case 1: out= mrs.getVisionPercept(curPosx+1,curPosy ); break;  //Looking East
			case 2: out= mrs.getVisionPercept(curPosx, curPosy-1);break; //Looking South
			case 3: out= mrs.getVisionPercept(curPosx-1, curPosy);break; //Looking West
		}
		
		return out;
		
	}
	//Getting VisionPercept in String
	public String getVisionPerceptString(){
		VisionPercept next = getVisionPercept();
		if(next==null){
			return "NULL";
		}else if(next.isClear()){
			return "CLEAR";
		}else {
			return "BOULDER";
		}
		
	}
	
	//Getting SamplePercept in Value
	public int getSamplePercept(){
		int out =0;
		int x = curPosx,y=curPosy;
		switch (visionDirection){
			case 0: y++; break;  //Looking North
			case 1: x++; break;  //Looking East
			case 2: y--; break; //Looking South
			case 3: x--; break; //Looking West
		}
		if (mrs.getSamplePercept(x, y)==null||!getVisionPercept().isClear()){  //out of the range of map or there is a BOULDER will return 0 as samplePercept value
			return 0;
		}else{
		    out= mrs.getSamplePercept(x, y).value();
		    return out;
		}
	}
	
	//Mark current tile as grabbed
	public void updateGrab(){
		mrs.grab(curPosx, curPosy);
		sumGrab++;
	}
	//Check current tile grabbed or not
	public boolean checkGrab(){
		return mrs.getgrabbed(curPosx, curPosy);
	}
	
	//Move to next tile according to visionDirection
	public boolean move(){
		if(getVisionPercept().isClear()){
			sumMove++;
			switch (visionDirection){
				case 0: curPosy++; break;  //Moving North
				case 1: curPosx++; break;  //Moving East
				case 2: curPosy--; break;  //Moving South
				case 3: curPosx--; break;  //Moving West		
			}
			return true;
		}else{return false;}
	}
	

	//If stuck, update next VisionDirection with a random value
	public void updateVisionDirection(){
		Random ran = new Random();
		int x = ran.nextInt(4) + 0;
		visionDirection=x; 
	}
	
}
