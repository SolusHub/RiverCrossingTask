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
This class does a time step and returns 1=goal reached, 0=terminated, -1= still going
*/
import java.util.ArrayList;

public class NormalPass {
    //uses pass instagated from 'RcTaskNew' to call functions from pass
    private Pass Pass;
    public NormalPass(Pass pas) {
        this.Pass = pas;
    }
    
    //inputs into the decision network and returns decision network outputs
    public ArrayList<Double> GetDN() {
        ArrayList<Double> DNInput = new ArrayList();
        for (int j=0;j<5;j++){//g,r,s,w,t
            if (j==Pass.obstacle) {DNInput.add(1.0);}
            else {DNInput.add(0.0);}
        }
        if (Pass.Carrying==1) {DNInput.add(1.0);}//c
        else  {DNInput.add(0.0);}
        
        return(Pass.DN.ForwardPass(DNInput));//returns DN output
    }
    
    //Determins if the AL needs to be updated before using
    public int NeedALUpdate(ArrayList<Double> DNoutput) {
        int IsSame=1;
        for (int i=0;i<Pass.DN.ajsize;i++) {
            double y = DNoutput.get(i);
            double x = Pass.PrevDNoutput.get(i);
            if (y!=x) {return(0);}
        }
        if (Pass.Neighbours()==Pass.NeighPos&&Pass.NeighPos==1) {return(1);}
        return(0);
    }
   
    
    //runs a time step 
    public int NormalStep() {
        //do DN
        ArrayList<Double> DNoutput=GetDN();
        //do actions
        int Continue = Pass.Actions(DNoutput);
        if (Continue==-1) {return(-1);}
        //game scenarios 
        int Fitness = Pass.GameScenarios();
        if (Fitness!=-1) {return(Fitness);}
        //check DN
        if (NeedALUpdate(DNoutput)==1) {return(Pass.SM.NewState());}//doesn't need AL update
        //update AL
        Pass.UpdatePrevDN(DNoutput);
        Pass.UpdateAL(DNoutput);
        return(Pass.SM.NewState());//0=terminate//1=complete//-1=continue
    }
}
