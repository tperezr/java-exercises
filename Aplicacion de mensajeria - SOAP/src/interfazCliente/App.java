package interfazCliente;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 550, 400);
		frame.setContentPane(new LoginCliente(frame));
		frame.validate();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
