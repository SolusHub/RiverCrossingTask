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
This class is the GUI
This class creates the visual representation of the environment which can be controlled by the user
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class VisualEnvironment extends JFrame{
    VisualAIMoves RCtask = new VisualAIMoves();//----------------------------- 
    int passthrough=1;
    int fitness=-1;
    // GUI Component dimentsions.
    private final int CANVAS_INITIAL_WIDTH = 800;
    private final int CANVAS_INITIAL_HEIGHT = 640;
    private final int CONTROL_PANEL_WIDTH = 200;
    private final int MESSAGE_AREA_HEIGHT = 100;
    
    Color black = new Color (0.0F, 0.0F, 0.0F);//0
    Color blue = new Color (0.0F, 0.0F, 1.0F);//1
    Color green = new Color (0.0F, 0.5F, 0.0F);//2
    Color red = new Color (1.0F, 0.0F, 0.0F);//3
    Color orange = new Color (1.0F, 0.5F, 0.0F);//4
    Color yellow = new Color (1.0F, 1.0F, 0.0F);//5
    Color pink = new Color (1.0F, 0.0F, 0.5F);//6
    Color purple = new Color (1.0F, 0.0F, 1.0F);//7
    Color lightblue = new Color (0.0F, 1.0F, 1.0F);//8
    Color darkpurple = new Color (0.5F, 0.0F, 0.5F);//9
    Color darkyellow = new Color (0.5F, 0.5F, 0.0F);//10
    Color teal = new Color (0.0F, 0.5F, 0.5F);//11
    Color lightgreen = new Color (0.5F, 1.0F, 0.5F);//12
    Color lightred = new Color (1.0F, 0.5F, 0.5F);//12
    ArrayList <Color> colours = new ArrayList(6);//front
    int count=0;
    
    
    private JButton Upbut, UpLeftbut, UpRightbut, MiddleLeftbut, MiddleRightbut, Downbut, DownLeftbut, DownRightbut, Animatbut;
    
    class Canvas extends JPanel
    {
        // Called every time there is a change in the canvas contents.
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            draw(g);
        }
    } // end inner class Canvas
    
    private Canvas canvas;
     private JPanel controlPanel;
      
    public VisualEnvironment() {
        RCtask.GetEnviro();//gets correct animat
        //RCtask.ExploreEnviro();//gets an animat
        
        
        // Canvas
        canvas = new Canvas();
          canvas.setBorder(new TitledBorder(new EtchedBorder(), "Canvas"));
          canvas.setPreferredSize(new Dimension(CANVAS_INITIAL_WIDTH, CANVAS_INITIAL_HEIGHT));
        add(canvas, BorderLayout.CENTER);
    
     // Control Panel
        controlPanel = new JPanel();
          controlPanel.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
          controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CANVAS_INITIAL_HEIGHT));
          add(controlPanel, BorderLayout.LINE_START); 
    
    
        
    //250
    colours.add(0,black);
    colours.add(1,blue);
    colours.add(2,green);
    colours.add(3,red);
    colours.add(4,orange);
    colours.add(5,yellow);
    
    
    //-------------------------------------------------------------------------------------        
//-------------------------------------------------------------------------------------           
    class Up implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.Up();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(0);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   Upbut = new JButton("Up");
          Upbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          Upbut.addActionListener(new Up() );
        controlPanel.add(Upbut);
    
    class UpLeft implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.UpLeft();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(1);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   UpLeftbut = new JButton("UpLeft");
          UpLeftbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          UpLeftbut.addActionListener(new UpLeft() );
        controlPanel.add(UpLeftbut);
    
    class UpRight implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.UpRight();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(2);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   UpRightbut = new JButton("UpRight");
          UpRightbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          UpRightbut.addActionListener(new UpRight() );
        controlPanel.add(UpRightbut);
    
    class MiddleLeft implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.MiddleLeft();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(3);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   MiddleLeftbut = new JButton("MiddleLeft");
          MiddleLeftbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          MiddleLeftbut.addActionListener(new MiddleLeft() );
        controlPanel.add(MiddleLeftbut);
    
    class MiddleRight implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.MiddleRight();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(4);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   MiddleRightbut = new JButton("MiddleRight");
          MiddleRightbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          MiddleRightbut.addActionListener(new MiddleRight() );
        controlPanel.add(MiddleRightbut);
    
    class Down implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.Down();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(5);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   Downbut = new JButton("Down");
          Downbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          Downbut.addActionListener(new Down() );
        controlPanel.add(Downbut);
        
    class DownLeft implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.DownLeft();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(6);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   DownLeftbut = new JButton("DownLeft");
          DownLeftbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          DownLeftbut.addActionListener(new DownLeft() );
        controlPanel.add(DownLeftbut);
        
    class DownRight implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                //RCtask.SM.Enviro.DownRight();
                int position = RCtask.Task.Pass.SM.ChangeNeuron(7);
                RCtask.Task.Pass.SM.CurrentPos=position;
                canvas.repaint();
            }
        }
   DownRightbut = new JButton("DownRight");
          DownRightbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
          DownRightbut.addActionListener(new DownRight() );
        controlPanel.add(DownRightbut);    
        
   
   class Animating implements ActionListener {
       public void actionPerformed(ActionEvent event) {
           System.out.println("----------------------------------------");
           fitness=(int) RCtask.AnimatStep();
           canvas.repaint();
           count+=1;
           System.out.println("Count: "+count);
           System.out.println("Fitness: "+fitness);
           System.out.println("Xaxis: "+RCtask.Task.Pass.SM.Enviro.Xaxis);
           System.out.println("Yaxis: "+RCtask.Task.Pass.SM.Enviro.Yaxis);
           System.out.println("CurrentPos: "+RCtask.Task.Pass.SM.CurrentPos);
           System.out.println("Object: "+RCtask.Task.Pass.SM.Enviro.objects.get(RCtask.Task.Pass.SM.CurrentPos));
           System.out.println("passed");
           
       }
   }
   
   Animatbut = new JButton("Next state");
        Animatbut.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH-20, 50));
        Animatbut.addActionListener(new Animating() );
      controlPanel.add(Animatbut);
        
      
    canvas.repaint();
    
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pack();
        setVisible(true);
    }    
        
        
        
        
    void draw(Graphics g) 
    { 
    int xwidth = CANVAS_INITIAL_WIDTH/23;//20 squares
    int yheight = CANVAS_INITIAL_HEIGHT/23;//20 sqaures
        System.out.println("Xaxis: " +RCtask.Task.Pass.SM.Enviro.Xaxis);
        System.out.println("Yaxis: " +RCtask.Task.Pass.SM.Enviro.Yaxis);
        
        
        int xtemp = xwidth*RCtask.Task.Pass.SM.Enviro.Xaxis;
        int ytemp= yheight*RCtask.Task.Pass.SM.Enviro.Yaxis;
        g.fillRect(xtemp+xwidth,(yheight*20)-ytemp,xwidth,yheight);
        
        for (int i=0;i<RCtask.Task.Pass.SM.Enviro.objects.size();i++) {
            int obstacle = RCtask.Task.Pass.SM.Enviro.objects.get(i);
            int pass=0;
            
            ////0=grass,1=resource,2=stone,3=water,4=trap
            if (obstacle==1) {g.setColor(green);pass=1;}//wall
            else if (obstacle==2) {g.setColor(orange);pass=1;}//door
            else if (obstacle==3) {g.setColor(blue);pass=1;}//gkey
            else if (obstacle==4) {g.setColor(red);pass=1;}//bkey
            
            int objXaxis = i%20*xwidth;
            int objYaxis = i/20*yheight;
            if (pass==1) {g.fillRect(objXaxis+xwidth,(yheight*20)-objYaxis,xwidth,yheight);}
            g.setColor(colours.get(0));
        }
        g.setColor(new Color (0.0F,0.0F, 0.0F));
        
        //draw grid
        for(int i=0; i<22;i++) {
            g.drawLine(xwidth, yheight*i, xwidth*21, yheight*i);//Horizontal lines repeated in the Y-axis
            g.drawLine(xwidth*i, yheight, xwidth*i, yheight*21);//vertical lines repeated in the X-axis
        }
    }
        
   
    
    
    public static void main(String args[])
    {
        
        System.out.println("yes");
        
        VisualEnvironment VisualEnvironment = new VisualEnvironment();
    } // end main method
    
    
}
