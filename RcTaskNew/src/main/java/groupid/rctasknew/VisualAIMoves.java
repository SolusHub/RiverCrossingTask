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
This class performs actions of the animat in the GUI
*/
public class VisualAIMoves {
    RcTaskNew Task = new RcTaskNew();
    
    public void ExploreEnviro() {
         Task.Begin(0);
         Task.Weight.WeightAdjust(9);
         Task.Pass.SM.AnimatReset();
     }
    
    
    //only used when environment.java is executed --------------------
    public void GetEnviro() {
        Task.Begin(1);
        int count=0;
        while (count==0) {//new environment
            System.out.println("Finding Successful Animat");
            for (int i=0;i<Task.Pass.NumAnimats;i++) {//new animat
                System.out.println("New Animat: "+ i);
                Task.Weight.WeightAdjust(i);
                int fitness=Task.Episode(1);
                if (fitness==100) {//fitness==100
                    System.out.println("SuCCess: "+i);
                    count=1;
                    i=967599;
                }
            }
        }
        //
        //WeightAdjust(1);
        Task.Pass.Reset(1);
        //Task.FromtheStart(X,Y);
        System.out.println("Output: "+Task.Pass.DN.Maj);
    }
     
    public double EnvironmentStep(int Carry) {
        System.out.println("Output: "+Task.Pass.DN.Maj);//prev output
        return(Task.Episode(1));
    }
    public double AnimatStep() {//does entire animat runthrough//returns fitness
        return(Task.Normal.NormalStep());
    }
}
//IN//g,r,s,w,t.c//6
//OUT//p,r,s,w,t//5
