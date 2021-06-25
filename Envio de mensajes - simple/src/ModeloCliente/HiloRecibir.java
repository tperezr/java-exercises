package ModeloCliente;

import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Modelo.Mensaje;
import Sockets.progredes.SocketClienteTCP;

public class HiloRecibir implements Runnable {

	private SocketClienteTCP socketCliente;
	private Mensaje mensaje;
	private JTextArea textArea;
	private JButton btnDesconectar;
	private JTextField textDesconectado;
	private JTextField textNick;
	private JTextField puerto;
	private JButton btnConectar;
	private JTextField ip;

	public HiloRecibir(SocketClienteTCP _socketCliente, JTextArea _textArea, JButton _btnConectar,
			JButton _btnDesconectar, JTextField _textDesconectado, JTextField _textNick, JTextField _puerto,
			JTextField _ip) {
		this.socketCliente = _socketCliente;
		this.textArea = _textArea;
		this.btnConectar = _btnConectar;
		this.btnDesconectar = _btnDesconectar;
		this.textDesconectado = _textDesconectado;
		this.textNick = _textNick;
		this.puerto = _puerto;
		this.ip = _ip;
	}

	@Override
	public void run() {
		try {
			synchronized (textArea) {
				while (socketCliente.getSocket().isConnected()) {
					mensaje = (Mensaje) socketCliente.recibirObjeto();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

							textArea.setText(textArea.getText() + "\n" + mensaje.toString() + " " + mensaje.getEmisor()
									+ ": " + mensaje.getMsj());
							textArea.setVisible(true);

						}
					});
				}
			}

		} catch (EOFException e) {
			JOptionPane.showMessageDialog(null, "Se desconectó el servidor", "Servidor desconectado  ~ CLIENTE",
					JOptionPane.INFORMATION_MESSAGE);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					btnConectar.setEnabled(true);
					puerto.setEditable(true);
					textNick.setEditable(true);
					ip.setEditable(true);

					textNick.setText("");
					puerto.setText("");
					ip.setText("");
					btnDesconectar.setEnabled(false);
					textDesconectado.setText("OFF");
					textDesconectado.setForeground(Color.RED);
				}
			});
			try {
				socketCliente.cerrar();
			} catch (NullPointerException e1) {
			} catch (EOFException e1) {
			} catch (IOException e1) {
			}
		} catch (SocketException e) {
			if (e.getMessage() == "Connection reset") {
				JOptionPane.showMessageDialog(null, "Se desconectó el servidor", "Servidor desconectado  ~ CLIENTE",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Te has desconectado correctamente", "Desconexión  ~ CLIENTE",
						JOptionPane.INFORMATION_MESSAGE);
			}
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					btnConectar.setEnabled(true);
					puerto.setEditable(true);
					textNick.setEditable(true);
					ip.setEditable(true);

					textNick.setText("");
					puerto.setText("");
					ip.setText("");
					btnDesconectar.setEnabled(false);
					textDesconectado.setText("OFF");
					textDesconectado.setForeground(Color.RED);
				}
			});
			try {
				socketCliente.cerrar();
			} catch (NullPointerException e1) {
			} catch (EOFException e1) {
			} catch (IOException e1) {
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			mensaje = new Mensaje();
			mensaje.setFechayhora(LocalDateTime.now());
			textArea.setText(textArea.getText() + "\n" + mensaje.toString() + " : " + "~  Se ha terminado la conexion al chat  ~");
		}
	}
}
