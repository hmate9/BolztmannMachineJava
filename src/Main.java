import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {  // Instantiate a textfield for input and a textarea for output.
   private JTextField input = new JTextField(15);
   private JTextArea output = new JTextArea(5, 15);

   public void TextFieldEx() {  
	   // Register a listener with the textfield
      TextFieldListener tfListener = new TextFieldListener();
      input.addActionListener(tfListener);

      // Don't let the user change the output.
      output.setEditable(false);

      // Add all the widgets to the applet
      this.getContentPane().add(input);
      this.getContentPane().add(output);
      input.requestFocus();        // start with focus on this field
   }

   // The listener for the textfield.
   private class TextFieldListener implements ActionListener {  
      public void actionPerformed(ActionEvent evt)
      {  String inputString = input.getText();
         output.append(inputString + "\n");
         input.setText("");
      }
   }
}
