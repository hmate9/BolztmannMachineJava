import java.util.Random;
 
 
public class Main {
 
        public static void main(String[] args) {
 
                final double learning = 0.1;
                final int visible = 6;
                final int hidden = 2;
               
                int[] states = new int[hidden + visible + 1];
                double[] weights = new double[hidden * visible + visible + hidden];
                int[] positiveE = new int[hidden * visible + visible + hidden];
                int[] negativeE = new int[hidden * visible + visible + hidden];
               
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
                int data = 1;
                
                while (count < 5000000) {
                	
                	if (data > 6) {
                		data = 1;
                	}
               
                for (int i = 0; i < visible; i++) {
                        // import training data
                	if (data == 1) {
                		states[i] = data1[i];
                	} else if (data == 2) {
                		states[i] = data2[i];
                	} else if (data == 3) {
                		states[i] = data3[i];
                	} else if (data == 4) {
                		states[i] = data4[i];
                	} else if (data == 5) {
                		states[i] = data5[i];
                	} else {
                		states[i] = data6[i];
                	}
                        
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
                        double kaki = r.nextDouble();
                        double prob = 1 / (1  + Math.exp(- sum));
                        if (prob > kaki) {
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
                        double kaki = r.nextDouble();
                        double prob = 1 / (1  + Math.exp(-sum));
                        if (prob > kaki) {
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
                        double kaki = r.nextDouble();
                        double prob = 1 / (1  + Math.exp(-sum));
                        if (prob > kaki) {
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
                        weights[i] += learning * ((positiveE[i] - negativeE[i])); // divide by 6?
                }
                data++;
                count++;
                }
                
                System.out.println("Harry Potter: " + weights[0] + "    " + weights[1]);
                System.out.println("Avatar: " + weights[2] + "    " + weights[3]);
                System.out.println("LOTR3: " + weights[4] + "    " + weights[5]);
                System.out.println("Gladiator: " + weights[6] + "    " + weights[7]);
                System.out.println("Titanic: " + weights[8] + "    " + weights[9]);
                System.out.println("Glitter: " + weights[10] + "    " + weights[11]);
                
 
        }
       
 
}