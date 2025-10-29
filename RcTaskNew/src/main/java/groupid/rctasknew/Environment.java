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
This class is the environment used to train the model
This class defines objects and movements in the environment
*/
import java.util.ArrayList;
import java.util.Random;

public class Environment {
    int Xaxis = 0;//current state along Xaxis
    int Yaxis = 0;//current state along Yaxis
    int EnvoChoice = 2;//0=easy//1=medium//2=hardest
    ArrayList <Integer> objects = new ArrayList(401);
    ArrayList <Integer> Tourneyobjects = new ArrayList(401);
    
    //initalise
    public void begin() {
        /*TO BE CLEAR, ARRAY VALUES WILL GO AS FOLLOWED:
        |
        |
        |40...
        |20,21,22...
        |0,1,2,3,4...
      x +- - - - - - - - - 
        y
        Therefore: Bottom left state = objects.get(0)
        Highest Array position = 399
        */
        //initialize
        for (int i=0;i<401;i++) {objects.add(0);}
        ResetEnvo();
    }
    
    //resets the environment to random starting state, creating a copy for tournaments.
    public void ResetEnvo() {
        
        Random rand = new Random();
        for (int i=0;i<401;i++) {
            objects.set(i,0);
        }
        for (int i=0;i<7;i++) {//set traps
            objects.set(rand.nextInt(400),4);//trap
        }
        for (int i=0;i<7;i++) {//set stone
            objects.set(rand.nextInt(400),2);//stone
        }
        for (int i=0;i<20;i++) {
            objects.set(i*20+13,3);//water
        }
        if (EnvoChoice==0) {//easiest
            objects.set(5*20+13, 0);
            objects.set(15*20+13, 0);
        }
        else if (EnvoChoice==2) {//hardest
            for (int i=0;i<20;i++) {
                objects.set(i*20+14,3);//water
            }
        }
        //set resource
        objects.set(240+17,1);//resource
        
        //create copy of environment for tournament
        Tourneyobjects = new ArrayList();
        for (int i=0;i<objects.size();i++) {
            Tourneyobjects.add(objects.get(i));
        }
    }
    
    //Resets environment back to copied envo for tournaments 
    public void ResetEnvoTourney() {
        //3 animats have the same environment, then change
        objects = new ArrayList();
        for (int i=0;i<Tourneyobjects.size();i++) {
            objects.add(Tourneyobjects.get(i));
        }
    }
    
    // 8 possible moves from any state. Remember to include limiters to stop going out of environment
    //The following functions define the movement possible that go to new states excluding 'GetArrayState()'
    public int GetArrayState() {
        int ans = Yaxis*20+Xaxis;
        return (ans);
    }
    
    
    public int Up() {
        Yaxis++;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    
    public int UpLeft() {
        Yaxis++;
        Xaxis--;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    public int UpRight() {
        Yaxis++;
        Xaxis++;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    public int MiddleRight() {
        Xaxis++;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    public int MiddleLeft() {
        Xaxis--;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    
    public int Down() {
        Yaxis--;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    public int DownRight() {
        Yaxis--;
        Xaxis++;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
    public int DownLeft() {
        Yaxis--;
        Xaxis--;
        int ans = Yaxis*20+Xaxis;
        return(ans);
    }
}
