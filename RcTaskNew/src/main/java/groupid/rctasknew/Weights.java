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
This class reads and writes files for weights and accuracy
This class changes and initialises weights for decision network
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Weights {
    int InputSize = 0;
    int HiddenSize = 0;
    int OutputSize = 0;
    int InputWeights = 0;
    int OutputWeights = 0;
    int BatchOfNeurons = 0;
    
    public void Begin() {
        InputSize = Pass.DN.aisize;
        HiddenSize = Pass.DN.ahsize;
        OutputSize = Pass.DN.ajsize;
        InputWeights = InputSize*HiddenSize;
        OutputWeights = OutputSize*HiddenSize;
        BatchOfNeurons = InputWeights+OutputWeights;//44
    }
    
    //uses pass instagated from 'RcTaskNew' to call functions from pass
    private Pass Pass;
    public Weights(Pass Pass) {
        this.Pass = Pass;
    }
    
    //gets decision network weights from file uploaded from last time program was executes 
    //appends weights to Decision network weight arrays
    public void FileWeight() {
        try {
            File WeightOut = new File("Weights2.txt");
            Scanner myReader = new Scanner(WeightOut);
            for (int j=0;j<Pass.NumAnimats;j++) {//for each animst
                for (int i =0;i<Pass.DN.Mwhi.size();i++) {//for input to hidden weights
                    String bruh = myReader.nextLine();
                    double temp = Double.parseDouble(bruh);
                    try{Pass.DN.AnimatWeights.add(temp);}//append to weight array
                    catch(IndexOutOfBoundsException e) {
                        System.out.println("HIDDEN NEURON ROM TO ARRAY PROBLEM");
                    }
                }
                for (int i =0;i<Pass.DN.Mwjh.size();i++) {// for hidden to output weights
                    String bruh = myReader.nextLine();
                    double temp = Double.parseDouble(bruh);
                    try{Pass.DN.AnimatWeights.add(temp);}//append to weight array
                    catch(IndexOutOfBoundsException e) {
                        System.out.println("OUTPUT NEURON ROM TO ARRAY PROBLEM");
                    }
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("FIle not found");
            e.printStackTrace();
        }
    }//end of reader
    
    //uploads weights from decision network into file
    public void FileUpload() {
        //transfer weights to ROM -----------------
        try {
            BufferedWriter WeightOuted = new BufferedWriter(new FileWriter("Weights2.txt"));
            for (int j=0;j<Pass.NumAnimats;j++) {
                for (int i=0;i<Pass.DN.Mwhi.size();i++) {//input hidden
                    String toletters = Double.toString(Pass.DN.AnimatWeights.get(j*BatchOfNeurons+i));
                    WeightOuted.write(toletters);
                    WeightOuted.newLine();
                }
                for (int i=0;i<Pass.DN.Mwjh.size();i++) {//hidden output
                    String toletters = Double.toString(Pass.DN.AnimatWeights.get(j*BatchOfNeurons+InputWeights+i));
                    WeightOuted.write(toletters);
                    WeightOuted.newLine();
                }
            }
            WeightOuted.close();
        }
        catch (IOException e) {
            System.out.println("File to store weights not found!!!!");
            e.printStackTrace();
        }
    }
    
    //uploads accuracy to file
    public void AccuracyFileUpload() {
        //transfer weights to ROM -----------------
        try {
            BufferedWriter WeightOuted = new BufferedWriter(new FileWriter("Accuracy.txt"));
                for (int i=0;i<Pass.Accuracy.size();i++) {
                    String toletters = Double.toString(Pass.Accuracy.get(i));
                    WeightOuted.write(toletters);
                    WeightOuted.newLine();
                }
            WeightOuted.close();
        }
        catch (IOException e) {
            System.out.println("File to store weights not found!!!!");
            e.printStackTrace();
        }
    }
    
    //initailse random weight values for all animats
    public void RandomWeight() {
        Random rand = new Random();
        //input to hidden
        for (int j=0;j<Pass.DN.Mwhi.size();j++) {
            double randval = rand.nextDouble(1);
            int posneg = rand.nextInt(2);
            if (posneg==1) {randval=-randval;}
            Pass.DN.Mwhi.set(j, randval);
            Pass.DN.AnimatWeights.add(randval);
        }
        //hidden to output
        for (int j=0;j<Pass.DN.Mwjh.size();j++) {
            double randval = rand.nextDouble(1);
            int posneg = rand.nextInt(2);
            if (posneg==1) {randval=-randval;}
            Pass.DN.Mwjh.set(j, randval);
            Pass.DN.AnimatWeights.add(randval);
        }
            
    }
    
    //changes the weights used by decsion network to another animat
    public void WeightAdjust(int AnimatPos) {//AnimatPos is the number of the animat changing weights to 
        for (int j=0;j<Pass.DN.Mwhi.size();j++) {
            double temp=Pass.DN.AnimatWeights.get(AnimatPos*BatchOfNeurons+j);
            Pass.DN.Mwhi.set(j, temp);
        }
        for (int j=0;j<Pass.DN.Mwjh.size();j++) {
            double temp=Pass.DN.AnimatWeights.get(AnimatPos*BatchOfNeurons+InputWeights+j);
            Pass.DN.Mwjh.set(j, temp);
        }
    }
}
