/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupid.rctasknew;

/**
 *
 * @author tompr
 */
/*
This class is used at each time step to determine actions and the reulst of those actions.
This class initialises Shunting model and decision network for all classes that use them.
*/
import java.util.ArrayList;

public class Pass {
    ShuntingModel SM = new ShuntingModel();
    DecisionNetwork DN = new DecisionNetwork();
    
    int Carrying=0;//1= animat carrying object/ 0=no
    int NeighPos=-1;//Are neighbour state values positive?//assume not at beginning
    int obstacle;//current state object
    ArrayList<Double> PrevDNoutput = new ArrayList();//DN output from last time step
    ArrayList<Double> Accuracy = new ArrayList();//accuracy of epoch
    int NumAnimats = 250;//number of animats used
    
    public void Begin() {//initialize
        for (int i=0;i<DN.ajsize;i++) {PrevDNoutput.add(0.0);}
        SM.Begin();
        DN.begin();
    }
    public void Reset(int NewTourney) {//Resets for new animat and environment depending on tournament
        Carrying=0;
        NeighPos=-1;
        for (int i=0;i<DN.ajsize;i++) {PrevDNoutput.set(i, 0.0);}
        if (NewTourney==1) {SM.Enviro.ResetEnvo();}
        else {SM.Enviro.ResetEnvoTourney();}
        SM.AnimatReset();
    }
    
     public int Neighbours() {//checks for positive neighbours. 0=negative, 1=positive
        int CurrentXaxis=SM.Enviro.Xaxis;//store X coordinate of animats current position
        int CurrentYaxis=SM.Enviro.Yaxis;//store Y...
        
        for(int i=0;i<8;i++) {
            int NewAnimatPos = SM.ChangeNeuron(i);//returns array pos of state
            double NewIotaVal = SM.Iota.get(NewAnimatPos);
            SM.Enviro.Xaxis=CurrentXaxis;
            SM.Enviro.Yaxis=CurrentYaxis;
            if (NewIotaVal<0) {return(0);}//return neg
        }
        double CurentIotaVal = SM.Iota.get(SM.CurrentPos);
        if (CurentIotaVal<0) {return(0);}//return neg
        //else
        return(1);
    }
    //return: 0=fail, -1=continue, 100=success 
    public int GameScenarios() {//if any game ending conditions have been met, this'll return the appropiate fitness score
        obstacle = SM.Enviro.objects.get(SM.CurrentPos);
        if (obstacle==1) {return(100);}//resource
        else if (obstacle==3) {return(0);}//water
        else if (obstacle==4) {return(0);}//trap
        return(-1);
    }
    //return: 0=fail, -1=continue, 100=success 
    public int Actions(ArrayList<Double> DNOutput) {//does any appropiate actions 
        //IN//g,r,s,w,t.c//6
        //OUT//p,r,s,w,t//5
        //0=grass,1=resource,2=stone,3=water,4=trap
        obstacle = SM.Enviro.objects.get(SM.CurrentPos);
        if (Carrying==0) {//not carrying
            if (obstacle!=2) {return(0);}//trying to pick up non stone object
            SM.Enviro.objects.set(SM.CurrentPos,0);//stone to grass
            NeighPos=-1;
            Carrying=1;
            return(-1);
        }
        else {//carrying
            if (obstacle==3) {//placing on water
                SM.Enviro.objects.set(SM.CurrentPos,0);//water to grass
                NeighPos=-1;
                Carrying=0;
                return(-1);
            }
            return(0);//not water
        }
    }
    
    public void UpdatePrevDN(ArrayList<Double> DNoutput) {//make current DN output prev DN output for next time step
        for (int i=0;i<DN.ajsize;i++) {
            Double temp = DNoutput.get(i);
            PrevDNoutput.set(i, temp);
        }
    }
    
    public void UpdateAL(ArrayList<Double> DNOutput) {//updates activation landscape 
        for (int j=0;j<400;j++){//should be done in SM!
            if (SM.Enviro.objects.get(j)==0) {SM.Iota.set(j, 0.0);}//grass
            else if (SM.Enviro.objects.get(j)==1) {SM.Iota.set(j, DNOutput.get(1)*15.0);}//resource
            else if (SM.Enviro.objects.get(j)==2) {SM.Iota.set(j, DNOutput.get(2)*15.0);}//stone
            else if (SM.Enviro.objects.get(j)==3) {SM.Iota.set(j, DNOutput.get(3)*15.0);}//water
            else if (SM.Enviro.objects.get(j)==4) {SM.Iota.set(j, DNOutput.get(4)*15.0);}//trap
            else {System.out.println("ERROR IN IOTA INITIALIZATION");}
        }
        NeighPos=Neighbours();//determines if all neighbours are positive
        SM.reset();//resets current AL array to 0.0
        for (int j=0;j<50;j++) {SM.AL();}//calculates AL for current value
    }
}
