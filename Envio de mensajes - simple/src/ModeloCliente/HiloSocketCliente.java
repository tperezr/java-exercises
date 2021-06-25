package ModeloCliente;

import java.awt.Color;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Modelo.Mensaje;
import Sockets.progredes.*;

public class HiloSocketCliente implements Runnable {

	private SocketClienteTCP socketCliente;
	private Mensaje mensaje;
	private JTextArea textArea;
	private JButton btnDesconectar;
	private JTextField textDesconectado;
	private JTextField textNick;
	private JTextField puerto;
	private JButton btnConectar;
	private JTextField ip;
	private Boolean verificacion;

	public HiloSocketCliente(SocketClienteTCP _socketCliente, Mensaje _mensaje, JTextArea _textArea,
			JButton _btnConectar, JButton _btnDesconectar, JTextField _textDesconectado, JTextField _textNick,
			JTextField _puerto, JTextField _ip) {
		this.socketCliente = _socketCliente;
		this.mensaje = _mensaje;
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
			socketCliente.conectar();
			socketCliente.enviar(textNick.getText());
			// socketexception
			verificacion = socketCliente.recibirBoolean();

			if (verificacion) {
				mensaje.setFechayhora(LocalDateTime.now());
				textArea.setText(mensaje.toString() + " : " + "~  Te has conectado al chat  ~");
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						btnConectar.setEnabled(false);
						btnDesconectar.setEnabled(true);
						textDesconectado.setForeground(Color.GREEN);
						textDesconectado.setText("ON");
					}
				});

				HiloRecibir hiloRecibir = new HiloRecibir(socketCliente, textArea, btnConectar,
						btnDesconectar, textDesconectado, textNick, puerto, ip);
				Thread thread = new Thread(hiloRecibir);
				thread.start();
			}
			else {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre", "User invalid ~ CLIENTE",
								JOptionPane.INFORMATION_MESSAGE);
						btnConectar.setEnabled(true);
						puerto.setEditable(true);
						textNick.setEditable(true);
						ip.setEditable(true);
						puerto.setText("");
						ip.setText("");
						textNick.setText("");
					}
				});
			}

		} catch (IllegalArgumentException e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, "Debe ingresar un número de puerto valido", "Invalid port ~ CLIENTE",
							JOptionPane.INFORMATION_MESSAGE);
					btnConectar.setEnabled(true);
					puerto.setEditable(true);
					textNick.setEditable(true);
					ip.setEditable(true);
					puerto.setText("");
					ip.setText("");
					textNick.setText("");
				}
			});

		} catch (NoRouteToHostException e) {
			JOptionPane.showMessageDialog(null, "Mal ingreso de IP. Por favor, reingrese los valores", "ERROR IP ~ CLIENTE",
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
				}
			});

		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Mal ingreso de IP. Por favor, reingrese los valores", "ERROR IP ~ CLIENTE",
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
				}
			});

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Mal ingreso de IP. Por favor, reingrese los valores", "ERROR IP ~ CLIENTE",
					JOptionPane.INFORMATION_MESSAGE);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					btnDesconectar.setEnabled(false);
					btnConectar.setEnabled(true);
					puerto.setEditable(true);
					textNick.setEditable(true);
					ip.setEditable(true);

					textNick.setText("");
					puerto.setText("");
					ip.setText("");
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}
}
