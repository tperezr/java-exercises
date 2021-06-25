package ModeloCliente;

import java.io.IOException;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Binario.ArchivoBinario;
import modeloMensajes.*;
import Sockets.progredes.SocketClienteTCP;
import interfazCliente.MainCliente;

public class HiloEnviar implements Runnable {

	private SocketClienteTCP socketCliente;
	private Mensaje mensaje;
	private JFrame frame;
	private ArchivoBinario ab1;
	public HiloEnviar(SocketClienteTCP _socketCliente, Mensaje _mensaje, JFrame _frame, ArchivoBinario _ab1 ) {
		this.socketCliente = _socketCliente;
		this.mensaje = _mensaje;
		this.frame = _frame;
		this.ab1 = _ab1;
	}

	@Override
	public void run() {
			try {
				if (mensaje != null) {
					socketCliente.enviarObjeto(mensaje);
				}
			} catch (SocketException e) {
				JOptionPane.showMessageDialog(null, "No esta establecida la conexion", "NULL CONNECTION  ~ CLIENTE",
						JOptionPane.INFORMATION_MESSAGE);
				frame.setContentPane(new MainCliente(frame, socketCliente, ab1));
				frame.revalidate();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
