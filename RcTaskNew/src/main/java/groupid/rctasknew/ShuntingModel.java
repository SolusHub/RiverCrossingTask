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
This class is the shunting model
This class calculates the activation landscape and moves animats to new states
*/
import java.util.ArrayList;
import java.util.Random;

public class ShuntingModel {
    Environment Enviro = new Environment();//changes graph coordinates and converts it into array position
    
    ArrayList <Double> Iota = new ArrayList(401);//iota values from decision network
    ArrayList <Double> Neuron = new ArrayList(401);//all neurons in netwrok. 1 neuron = 1 environment cell(state)//cell position 401 is for outside of environment
    ArrayList <Double> NewNeuron = new ArrayList(401);//all neurons in netwrok. 1 neuron = 1 environment cell
    int CurrentPos =0; // current position of the animat
    
    //gets the position value of a neigbouring state depending on the direction
    public int ChangeNeuron(int input) {
        int NeuronPosition = 0;
    switch(input) {//get new array position of neuron
            case 0 -> NeuronPosition=Enviro.Up();
            case 1 -> NeuronPosition=Enviro.UpLeft();
            case 2 -> NeuronPosition=Enviro.UpRight();
            case 3 -> NeuronPosition=Enviro.MiddleLeft();
            case 4 -> NeuronPosition=Enviro.MiddleRight();
            case 5 -> NeuronPosition=Enviro.Down();
            case 6 -> NeuronPosition=Enviro.DownLeft();
            case 7 -> NeuronPosition=Enviro.DownRight();
            case 8 -> NeuronPosition=Enviro.GetArrayState();
        }
    if (Enviro.Xaxis<0||Enviro.Yaxis<0||Enviro.Yaxis>=20||Enviro.Xaxis>=20) {//if outside of environment
        NeuronPosition=400;
    }
    return(NeuronPosition);
    }
    
    //resets animat position to starting point
    public void AnimatReset() {
         Random rand = new Random();
        int loop=1;
        while (loop==1) {
            Enviro.Yaxis = rand.nextInt(20);
            Enviro.Xaxis = rand.nextInt(6);
            CurrentPos=ChangeNeuron(8);
            if (Enviro.objects.get(CurrentPos)==0) {loop=0;}
        }
    }
    
    //initializes all array's
    public void Begin() {
        for(int i=0;i<401;i++) {
            Iota.add(0.0);
            Neuron.add(0.0);
            NewNeuron.add(0.0);
        }
        Enviro.begin();
    }
    
    //Reset AL
    public void reset() {
        for (int i=0;i<400;i++) {
            Neuron.set(i,0.0);
        }
    }
    
    //calculate the activation function of a neuron
    public double AF() {
        //xi=1/8 * sum(xj=>0)+I
        CurrentPos=ChangeNeuron(8);//ensures xi correct (xi=position of current neuron)
        int CurrentXaxis=Enviro.Xaxis;//keep xi coordinates
        int CurrentYaxis=Enviro.Yaxis;//keep xi coordinates
        double sum =0.0;
        
        //sum---------------
        for(int i=0;i<8;i++) {
            int xjpos = ChangeNeuron(i);
            double temp = Neuron.get(xjpos);
            if (temp<0) {temp=0.0;}//xj=>0 (xj=neighbouring neuron)
            sum+=temp;
            Enviro.Xaxis=CurrentXaxis;
            Enviro.Yaxis=CurrentYaxis;
        }
        //*1/8-----------
        sum=sum/8.0;
        //+I-------------
        sum+=Iota.get(CurrentPos);
        //return 1/8 * sum(xj=>0)+I ---------
        return(sum);
    }
    
    //calculate activation landscape//does AF for each neuron 
    public void AL() {
        //xi=1/8 * sum(xj=>0)+I
        //do equation for every neuron(400)
        
        //save current pos--------------------
        CurrentPos=ChangeNeuron(8);//this is changing it?//x and y not correct//why?
        int tempCurrentPos = CurrentPos;
        int CurrentXaxis=Enviro.Xaxis;
        int CurrentYaxis=Enviro.Yaxis;
        
        //do AF for all neurons -----------------
        for (int i=0;i<400;i++) {
            CurrentPos=i;
            Enviro.Xaxis=i%20;
            Enviro.Yaxis=i/20;
            double temp = AF();
            NewNeuron.set(i,temp);
        }
        
        //change current to new AL----------------------
        for (int i=0;i<400;i++) {
            Neuron.set(i, NewNeuron.get(i));
        }
        
        //return to current pos--------------------
        Enviro.Xaxis=CurrentXaxis;
        Enviro.Yaxis=CurrentYaxis;
        CurrentPos=tempCurrentPos;
        //System.out.println("Iota"+Enviro.objects.get(13));
        //System.out.println("AL "+Neuron.get(13));
    }
    
    //If a neighbouring value>0, go to highest neighbouring state(changes position value to new states')
    public int NewState() {
        int CurrentXaxis=Enviro.Xaxis;
        int CurrentYaxis=Enviro.Yaxis;
        int High = HighestNeighbour(CurrentXaxis, CurrentYaxis);//gets direction number of state from current state for ChangeNeuron()
        if (High==9) {return(0);}//terminate 
        CurrentPos = ChangeNeuron(High);
        return(-1);//continue
    }
    
    //calculates highest neigbour value//returns direction of highest neighbour
    public int HighestNeighbour(int CurrentXaxis, int CurrentYaxis) {
        int highestPos=0;
        double highest = 0.0;
        for(int i=0;i<8;i++) {
            int xjpos = ChangeNeuron(i);
            double temp = Neuron.get(xjpos);
            if (temp>highest) {
                highest=temp;
                highestPos=i;
            }
            Enviro.Xaxis=CurrentXaxis;
            Enviro.Yaxis=CurrentYaxis;
        }
        if (highest>0){return(highestPos);}
        return(9);
    }
}
