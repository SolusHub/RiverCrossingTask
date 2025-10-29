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
This class contains the decision network
This class contains the activation function and forward pass to deliver decision network outputs.
*/
import java.util.ArrayList;
import java.util.Random;

public class DecisionNetwork {
    int aisize = 6;//input neurons
    int ahsize = 4;//hidden neurons
    int ajsize = 5;//output neurons
    ArrayList <Double> Mwhi = new ArrayList(aisize*ahsize);//weight input to hidden
    ArrayList <Double> Mwjh = new ArrayList(ahsize*ajsize);//weight hidden to output
    ArrayList <Double> Mai = new ArrayList(aisize);//input
    ArrayList <Double> Mah = new ArrayList(ahsize);//hidden
    ArrayList <Double> Maj = new ArrayList(ajsize);//output
   ArrayList<Double> AnimatWeights = new ArrayList();
   
    public void begin() {//initializes all array's
        Random rand = new Random();
        for (int i=0;i<aisize*ahsize;i++) {
            double randval = rand.nextDouble(1);
            Mwhi.add(randval);
        }
        for (int i=0;i<ahsize*ajsize;i++) {
            double randval = rand.nextDouble(1);
            Mwjh.add(randval);
        }
        for (int i=0;i<aisize;i++) {Mai.add(0.0);}
        for (int i=0;i<ahsize;i++) {Mah.add(0.0);}
        for (int i=0;i<ajsize;i++) {Maj.add(0.0);}
    }
   
    //calculates the output for a neuron
    public void TanhNeuron (ArrayList <Double> a, ArrayList <Double> b, ArrayList <Double> w, int size, int neuron){//Returns output of a neuron using the tanh activation function
        //a=input array, b=output array, w=weights, size=size of array a, neuron= current neuron
        //get sum of input and weights
        double sum = 0.0;
        for (int i=0;i<a.size();i++){
            double  input = a.get(i);
            double weight = w.get(i+size*neuron);
            sum += input*weight;
        }
        //tanh activation function
        //f(x)=2/(1+e^(-2x))-1
        double e=Math.exp(-2.0*sum);
        double ans=1.0+e;
        ans=2.0/ans;
        ans=ans-1.0;
        //add to output array
        b.set(neuron,ans);
    }
    
    //does a pass through the whole decision network//returns output values 
    public ArrayList ForwardPass (ArrayList<Double> input){
        //calculate hidden layer(mah) neurons
        for (int i=0; i<ahsize;i++) {TanhNeuron(input,Mah,Mwhi,aisize,i);}
        //calculate output layer(maj) neurons
        for (int i=0; i<ajsize;i++) {TanhNeuron(Mah,Maj,Mwjh,ahsize,i);}
        for (int i=0;i<ajsize;i++) {//set thresholds
            double temp = Maj.get(i);
            if (temp>0.3) {Maj.set(i,1.0);}
            else if (temp<-0.3) {Maj.set(i, -1.0);}
            else {Maj.set(i,0.0);}
        }
        //return output layer neurons
        return(Maj);
    }
}
