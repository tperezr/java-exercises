package InterfazCliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainCliente extends JFrame {
	private Cliente cliente;
	private JPanel mainPanel;
	private static MainCliente frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainCliente();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	public MainCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 440);
		cliente = new Cliente();
		mainPanel = cliente;
		setContentPane(mainPanel);
	}
}
