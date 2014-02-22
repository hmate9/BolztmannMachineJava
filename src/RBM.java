import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class RBM extends Applet implements KeyListener {
	
	private final double LEARNING = 0.1; // learning rate
    private int visible;
    private int hidden;
    
    boolean showRBM = false;
    
    private int[] trainingData;
    
    int trainingCases = 0;
    int counter = 0;
    int counter2 = 0;
    
    String setupInstruction = "How many visible nodes?";
    String keyboard = "";
    
    int setup = 2; // what stage of the setup we are at
   
    int[] states;
    double[] weights;
    int[] positiveE;
    int[] negativeE;
    
    @Override
	public void keyTyped(KeyEvent e) {
		// must implement
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// must implement
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		int x = Integer.parseInt(c + "");
		switch (setup) {
		case 2:
			// get visible nodes
			visible = x;
			setup++;
			keyboard += c;
			setupInstruction = "How many hidden nodes?";
			break;
		case 3:
			// get hidden nodes
			keyboard = "";
			hidden = x;
			setup++;
			keyboard += c;
			setupInstruction = "How many training sets?";
			break;
		case 4:
			// get training data number
			keyboard = "";
			trainingCases = x;
			trainingData = new int[visible * trainingCases];
			setup++;
			keyboard = "";
			setupInstruction = "Please enter the data for training set " + String.valueOf(counter + 1);
			break;
		default:
			// get learning data
			if (counter < trainingCases) {
				if (counter2 < visible) {
					setupInstruction = "Please enter the data for training set " + String.valueOf(counter + 1);
					trainingData[setup - 5] = x;
					keyboard += c;
					counter2++;
					setup++;
				if (!(counter2 < visible)) {
					keyboard = "";
					counter++;
					setupInstruction = "Please enter the data for training set " + String.valueOf(counter + 1);
					counter2 = 0;
					}
				}
			 if (!(counter < trainingCases)) {
				 setupInstruction = "Here are the results...";
				calculateRBM();
			 	}
			}
			break;
		}
		
		repaint();
        e.consume();

		
	}
	
	public void paint(Graphics g) {
		
		g.drawString(setupInstruction, 10, 20);
		g.drawString(keyboard, 10, 40);
		
		if (showRBM) {
			int temp = 0;
			for (int i = 0; i < visible * hidden; i += hidden) {
				for (int j = 0; j < hidden; j++) {
					g.drawString("" + weights[i + j], 10 + (j * 170), 40 + (temp * 20));
				}
				temp++;
			}
		}
            
	    }
 
		public void init() {
			addKeyListener( this );
        }
		
		private void calculateRBM() {
			
			states = new int[hidden + visible + 1];
		    weights = new double[hidden * visible + visible + hidden];
		    positiveE = new int[hidden * visible + visible + hidden];
		    negativeE = new int[hidden * visible + visible + hidden];
		    
                for (int i = 0; i < weights.length; i++) {
                	Random q = new Random();
                        weights[i] = 0.01 * q.nextDouble();
                }
               
                for (int i = 0; i < states.length - 1; i++) {
                        states[i] = 0;
                }
               
                states[states.length - 1] = 1; // bias is always on
               
                // training data
                int[] data1 = {1,1,1,0,0,0};
                int[] data2 = {1,0,1,0,0,0};
                int[] data3 = {0,0,1,1,1,0};
                int[] data4 = {1,1,1,0,0,0};
                int[] data5 = {0,0,1,1,0,0};
                int[] data6 = {0,0,1,1,1,0};
                // end training data
                
                int count = 0;
                int data = 0;
            
                while (count < 3000000) {
                	
                	if (data > trainingCases - 1) {
                		data = 0;
                	}
                	
               
                for (int i = 0; i < visible; i++) {
                        // import training data
                		states[i] = trainingData[i + (data * visible)];
                	  
                }
               
                for (int i = 0; i < hidden; i++) {
                        // update the hidden nodes
                        double sum = 0;
                        int k = 0;
                        for (int j = i; j < visible * hidden; j += hidden) {
                                sum += weights[j] * states[k];
                                k++;
                        }
                        sum += weights[hidden * visible + visible + i]; // add bias energy
                        Random r = new Random();
                        double randomNum = r.nextDouble();
                        double prob = 1 / (1  + Math.exp(- sum));
                        if (prob > randomNum) {
                                states[visible + i] = 1;
                        } else {
                                states[visible + i] = 0;
                        }
                       
                }
                
                // compute positive energies
                int k = 0;
                for (int i = 0; i < visible; i++) {
                        for (int j = 0; j < hidden; j++) {
                                positiveE[k] = states[i] * states[visible + j];
                                k++;
                        }
                }
                for (int i = 0; i < visible + hidden; i++) {
                        positiveE[k] = states[i] * states[states.length - 1];
                        k++;
                }
                
                
               
                for (int i = 0; i < visible * hidden; i += hidden) {
                        double sum = 0;
                        k = 0;
                        for (int j = 0; j < hidden; j ++) {
                                sum += weights[i + j] * states[k + visible];
                                k++;
                        }
                        sum += weights[hidden * visible + (i / hidden)]; // add bias energy
                        Random r = new Random();
                        double randomNum = r.nextDouble();
                        double prob = 1 / (1  + Math.exp(-sum));
                        if (prob > randomNum) {
                                states[(i / hidden)] = 1;
                        } else {
                                states[(i / hidden)] = 0;
                        }
                }
                
                
               
                for (int i = 0; i < hidden; i++) {
                        // update the hidden nodes
                        double sum = 0;
                        k = 0;
                        for (int j = i; j < visible * hidden; j += hidden) {
                                sum += weights[j] * states[k];
                                k++;
                        }
                        sum += weights[hidden * visible + visible + i]; // add bias energy
                        Random r = new Random();
                        double randomNum = r.nextDouble();
                        double prob = 1 / (1  + Math.exp(-sum));
                        if (prob > randomNum) {
                                states[visible + i] = 1;
                        } else {
                                states[visible + i] = 0;
                        }
                }
               
                // compute negative energies
                k = 0;
                for (int i = 0; i < visible; i++) {
                	for (int j = 0; j < hidden; j++) {
                		negativeE[k] = states[i] * states[visible + j];
                        k++;
                    }
                }
                for (int i = 0; i < visible + hidden; i++) {
                	negativeE[k] = states[i] * states[states.length - 1];
                    k++;
                }
                               
                                
                // update weights
                for (int i = 0; i < weights.length; i++) {
                        weights[i] += LEARNING * ((positiveE[i] - negativeE[i]));
                }
                data++;
                count++;
                }
                
                showRBM = true;
                repaint();
            
		}

}