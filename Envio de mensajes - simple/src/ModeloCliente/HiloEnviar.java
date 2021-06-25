package ModeloCliente;

import java.io.IOException;
import java.net.SocketException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Modelo.Mensaje;
import Sockets.progredes.SocketClienteTCP;

public class HiloEnviar implements Runnable {

	private SocketClienteTCP socketCliente;
	private Mensaje mensaje;
	private JTextArea textarea;
	private JTextField msj;

	public HiloEnviar(SocketClienteTCP _socketCliente, Mensaje _mensaje, JTextArea _textArea, JTextField _msj) {
		this.socketCliente = _socketCliente;
		this.mensaje = _mensaje;
		this.textarea = _textArea;
		this.msj = _msj;
	}

	@Override
	public void run() {
		synchronized (socketCliente) {
			try {
				if(!mensaje.getMsj().isEmpty()) {
					socketCliente.enviarObjeto(mensaje);
				}
			} catch (SocketException e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						textarea.setText("");
						msj.setText("");
					}
				});
				JOptionPane.showMessageDialog(null, "No esta establecida la conexion", "NULL CONNECTION  ~ CLIENTE",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
