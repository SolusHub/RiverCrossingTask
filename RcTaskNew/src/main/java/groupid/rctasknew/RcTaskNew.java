/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package groupid.rctasknew;

/**
 *
 * @author tompr
 */

/*
MAIN CLASS - execute to run training - execute 'VisualEnvironment' to show run with gui
*/
import java.util.ArrayList;
import java.util.Random;


public class RcTaskNew {
    //'NormalPass' and 'Weights' use attributes of classes initialised by pass.
    Pass Pass = new Pass();
        NormalPass Normal = new NormalPass(Pass);
        Weights Weight = new Weights(Pass);
    // FitnessScores and Animatorder are used to transfer results of an episode to the training class
    ArrayList<Integer> FitnessScores = new ArrayList();
    ArrayList <Integer> Animatorder = new ArrayList();
    
    
    /*
    initialises classes excluding 'UpdateWeights' and Visual classes
    initialises animats' Decision network weights randomly or from this programs previous execution
    */
    public void Begin(int ROM) {
        //initialises attributes across classes used to execute model. 
        Pass.Begin();
        Weight.Begin();
        if (ROM==1) {Weight.FileWeight();}
        else {for(int i=0;i<Pass.NumAnimats;i++){Weight.RandomWeight();}}
    }
    
    /*
    Runs an animat through the environment with a maximum of 100 time steps. 
    Returns 100=success or 0=fail
    */
    public int Episode(int NewTourney) {
        //resets attributes before attempting environment
        Pass.Reset(NewTourney);
        int AniReturn=-1;
        int count=0;
        //loops time steps untill a terminal condition is met
        while (count<=100) {//1000
            AniReturn=Normal.NormalStep();
            if (AniReturn!=-1) {return(AniReturn);}
            count++;
        }
        return(0);//0
    }
    
    /*
    Runs 249 animats split into 83 tournaments. 
    Randomly splits animats into tournaments of 3, using a new random environment per tournament
    Results are appended into an array for training in order of tournament.
    Outputs summated accuracy of each animat.
    */
    public double Epoch() {
        //reset attributes
        double sum=0.0;
        Random rand = new Random();
        FitnessScores=new ArrayList();
        Animatorder = new ArrayList();
        ArrayList <Integer> counting = new ArrayList(Pass.NumAnimats);
        //split animats randomly into tournaments of 3
        for (int i=0;i<Pass.NumAnimats;i++) {counting.add(i);}//0-249
        int dividen = Pass.NumAnimats/3;//83
        for (int i=0;i<dividen;i++) {
            int NewTourney =1;
            for (int j=0;j<3;j++) {
                int step=rand.nextInt(counting.size());
                int temp = counting.get(step);
                Animatorder.add(temp);
                counting.remove(step);
                Weight.WeightAdjust(temp);
                int fitness=Episode(NewTourney);//1 animat run in environment
                sum+=fitness;
                FitnessScores.add(fitness);
                NewTourney=0;
            }
        }
        sum=sum/Pass.NumAnimats;
        Pass.Accuracy.add(sum);
        System.out.println("accuracy of current tournament: "+sum);
        return(sum);
    }
    
    /*
    main executes the training cycle. All classes and attributes are instigated and initialised before cycling training
    if accuracy wasn't a significant value after 300 epochs, training was considered unsuccessfull
    'Task.Begin()' determined if weights were initialised or taken from file.
    The weight file is updated when accuracy is greater than 80 so weights can be used on the next environment after program terminates
    */
    public static void main(String[] args) {
        //Initialize ----------
        RcTaskNew Task = new RcTaskNew();
        UpdateWeights Train = new UpdateWeights(Task);
        Task.Begin(1);//1=get stored weights//0=random weights
        Train.Begin();
        for (int i=0;i<300;i++) {
            System.out.println("Tournament: "+(i+1));
            double Summ = Task.Epoch();
            if (Summ>80) {i=500000;}
            else{Train.bap();}
        }
        Task.Weight.FileUpload();
        Task.Weight.AccuracyFileUpload();
        
    }
}
