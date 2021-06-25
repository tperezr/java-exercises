package interfazCliente;

import javax.swing.JFrame;

import Binario.ArchivoBinario;
import Sockets.progredes.SocketClienteTCP;


public class App {
	public static void main(String[] args) {
		SocketClienteTCP socket = new SocketClienteTCP();
		ArchivoBinario archivoBinario = new ArchivoBinario();
		archivoBinario.setArchivo("Memoria.bin");
		JFrame frame = new JFrame();
		//frame.setLocation(300, 200);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 350);
		frame.setContentPane(new MainCliente(frame, socket,archivoBinario));
		frame.validate();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}

}
