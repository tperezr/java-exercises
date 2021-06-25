package estructuraTP.vista;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		  JFrame frame = new JFrame("A JFrame");
	      frame.setLocation(300,200);
	      frame.setVisible(true);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setContentPane(new Inicio(frame));
	      frame.validate();

	}

}