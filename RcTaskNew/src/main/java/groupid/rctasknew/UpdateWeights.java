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
This class is used to train the decision network
This class creates the offspring(changes the weights of the worst perfromer from tournament) during training
*/
import java.util.ArrayList;
import java.util.Random;

public class UpdateWeights {
    //gets instance from 'RcTaskNew'
    private RcTaskNew Task;
    public UpdateWeights(RcTaskNew RCtask) {
        this.Task = RCtask;
    }
    
    int InputSize = 0;
    int HiddenSize = 0;
    int OutputSize = 0;
    int InputWeights = 0;
    int OutputWeights = 0;
    int BatchOfNeurons = 0;
    
    //initialise variables
    public void Begin() {
        InputSize = Task.Pass.DN.aisize;
        HiddenSize = Task.Pass.DN.ahsize;
        OutputSize = Task.Pass.DN.ajsize;
        InputWeights = InputSize*HiddenSize;
        OutputWeights = OutputSize*HiddenSize;
        BatchOfNeurons = InputWeights+OutputWeights;//44
    }
    
    ArrayList <Double> p1check = new ArrayList();
    ArrayList <Double> p2check = new ArrayList();
    ArrayList <Double> Ap1check = new ArrayList();
    ArrayList <Double> Ap2check = new ArrayList();
    
    //adds mutation to weight given probability
    public double WeightMutation(double temp) {
        Random rand = new Random();
        double WeightMutate = rand.nextDouble();
                    if (WeightMutate<=0.001) {
                        double addMutate = rand.nextDouble(0.4);
                        int posneg = rand.nextInt(2);
                        if (posneg==1) {addMutate=-addMutate;}
                        temp+=addMutate;
                        if (temp>1) {temp=1;}
                        else if (temp<-1) {temp=-1;}
                    }
                    return (temp);
    }
    
    //creates chromoses for new offspring doing all hidden or output chromosomes at once  
    public void chromosome(int WorseAnimat, int parentAnimat1, int parentAnimat2, int leftNeurons, int rightNeurons, int DoneNeurons) {
        //probability chromosome is taken from parent or merged from both using single point crossover
        Random rand = new Random();
        for (int i=0;i<rightNeurons;i++) {
            double chromeProb = rand.nextDouble();
            int worseweight = (WorseAnimat*BatchOfNeurons)+(i*leftNeurons)+DoneNeurons;//Posworseweights+poshiddenweights+numdone
            //0.95                            0*animatweightsize+0*12+inputwieghtsize
            if (chromeProb<=0.95) {//1 parent chromosome
                int Choice = rand.nextInt(2);
                int parentpos;
                if (Choice==0) {parentpos=parentAnimat1;}
                else {parentpos=parentAnimat2;}
                int wieghtpos = (parentpos*BatchOfNeurons)+(i*leftNeurons)+DoneNeurons;
                //add chromosome to weight array
                for (int j=0;j<leftNeurons;j++) {
                    double temp = Task.Pass.DN.AnimatWeights.get(wieghtpos+j);
                    temp=WeightMutation(temp);
                    Task.Pass.DN.AnimatWeights.set(worseweight+j,temp);
                }
            }
            //0.05
            else {//single point crossover
                int wieghtpos1 = (parentAnimat1*BatchOfNeurons)+(i*leftNeurons)+DoneNeurons;
                int wieghtpos2 = (parentAnimat2*BatchOfNeurons)+(i*leftNeurons)+DoneNeurons;
                int CrossLine = rand.nextInt(leftNeurons+1);
                for (int j=0;j<CrossLine;j++) {
                    double temp = Task.Pass.DN.AnimatWeights.get(wieghtpos1+j);
                    temp=WeightMutation(temp);
                    Task.Pass.DN.AnimatWeights.set(worseweight+j,temp);
                }
                for (int j=0;j<leftNeurons-CrossLine;j++) {//12-11=1
                    double temp = Task.Pass.DN.AnimatWeights.get(wieghtpos2+j+CrossLine);//pos+0+11
                    temp=WeightMutation(temp);
                    Task.Pass.DN.AnimatWeights.set(worseweight+j+CrossLine,temp);
                }
            }
            
        }
        
    }
    
    //takes all tournaments and scores to create offspring
    public void bap() {
        p1check = new ArrayList();
        p2check = new ArrayList();
        Ap1check = new ArrayList();
        Ap2check = new ArrayList();
        
        Random rand = new Random();
        ArrayList <Integer> animrands = new ArrayList(3);
        ArrayList <Integer> Score = new ArrayList(3);
        ArrayList <Integer> counting = new ArrayList(Task.Pass.NumAnimats);
        //all animats
        for (int i=0;i<Task.Pass.NumAnimats;i++) {counting.add(i);}//0-249
        int dividen = Task.Animatorder.size()/3;//83
        //get results from tournament
        for (int i=0;i<dividen;i++) {
            animrands = new ArrayList(3);//new
            Score = new ArrayList(3);
            for (int j=0;j<3;j++) {
                animrands.add(Task.Animatorder.get(3*i+j));
                Score.add(Task.FitnessScores.get(3*i+j));
            }
            //create offspring
            GenAlg(animrands,Score);
        }
        //ensures weights during training aren't mixed up
        for (int i=0;i<p1check.size();i++) {if (p1check.get(i)!=Ap1check.get(i)) {System.out.println("P1 INCORRECT");}}
        for (int i=0;i<p2check.size();i++) {if (p2check.get(i)!=Ap2check.get(i)) {System.out.println("P2 INCORRECT");}}
    }
    
    //takes 3 animats in the saem tournament and replaces worst performer with offsprirng (assuming not all have 100 fitness score)
    public void GenAlg(ArrayList <Integer> animrands, ArrayList <Integer> Score) {
        int WorseCase=100;
        int WorsePos=99999999;
        for (int i=0;i<3;i++) {
            int fitness=Score.get(i);
            if (WorseCase>fitness) {
                WorseCase=fitness;//0
                WorsePos=i;//0,1,2
            }
        }
        if (WorseCase!=100) {//not all correct then create offspring
            int WorseAnimat = animrands.get(WorsePos);
            int parentAnimat1 = animrands.get((WorsePos+1)%3);//position in neuron array
            int parentAnimat2 = animrands.get((WorsePos+2)%3);
            
            
            //ensure parent weights haven't been messed with. Don't delete for training
            for (int i=0;i<BatchOfNeurons;i++) {
                p1check.add(Task.Pass.DN.AnimatWeights.get((parentAnimat1*BatchOfNeurons)+i));
                p2check.add(Task.Pass.DN.AnimatWeights.get((parentAnimat2*BatchOfNeurons)+i));
            }
            
            chromosome(WorseAnimat, parentAnimat1, parentAnimat2, InputSize, HiddenSize, 0);
            chromosome(WorseAnimat, parentAnimat1, parentAnimat2, HiddenSize, OutputSize, InputWeights);
            for (int i=0;i<BatchOfNeurons;i++) {
                Ap1check.add(Task.Pass.DN.AnimatWeights.get((parentAnimat1*BatchOfNeurons)+i));
                Ap2check.add(Task.Pass.DN.AnimatWeights.get((parentAnimat2*BatchOfNeurons)+i));
            }
        }//else all correct and end
    }
}
