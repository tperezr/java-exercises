package ModeloCliente;

import java.awt.Color;
import java.awt.Component;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Binario.ArchivoBinario;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeChat;
import Sockets.progredes.*;
import interfazCliente.MainCliente;
import interfazCliente.InicioCliente;

public class HiloSocketCliente implements Runnable {

	private SocketClienteTCP socketCliente;
	private Mensaje mensaje;
	private JTextField username;
	private JPasswordField password;
	private String validacion;
	private JTextField alias;
	private JFrame frame;
	private ArchivoBinario ab;
	private String aliasUser;

	public HiloSocketCliente(JFrame _frame, SocketClienteTCP _socketCliente, Mensaje mensaje, JTextField username,
			JPasswordField password, ArchivoBinario ab) { // login
		this.frame = _frame;
		this.socketCliente = _socketCliente;
		this.mensaje = mensaje;
		this.username = username;
		this.password = password;
		this.ab = ab;
	}

	public HiloSocketCliente(JFrame _frame, SocketClienteTCP _socketCliente, Mensaje mensajeObjeto, JTextField alias,
			JTextField nombreUsuario, JPasswordField contraseña) { // registro
		this.frame = _frame;
		this.socketCliente = _socketCliente;
		this.mensaje = mensajeObjeto;
		this.alias = alias;
		this.username = nombreUsuario;
		this.password = contraseña;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			socketCliente.conectar();
			HiloEnviar hilo = new HiloEnviar(socketCliente, mensaje, frame, ab);
			Thread enviarHilo = new Thread(hilo);
			enviarHilo.start();
			/*
			 * 1.usuario no encontrado 2.pass erronea 3.usuario conectado 4. usuario con el
			 * mismo username 5. usuario con el mismo alias 6. se conecta
			 */
			validacion = socketCliente.recibir();
			int validar = Integer.parseInt(validacion);
			switch (validar) {
			case 1: // usuario no encontrado
				JOptionPane.showMessageDialog(null, "Su nombre de usuario es erroneo, reingrese valores.",
						"Wrong username", JOptionPane.INFORMATION_MESSAGE);
				password.setText("");
				username.setText("");
				break;
			case 2: // pass erronea
				JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Wrong password",
						JOptionPane.INFORMATION_MESSAGE);
				password.setText("");
				break;
			case 3: // usuario conectado
				JOptionPane.showMessageDialog(null, "Usuario en linea. No puede haber dos sesiones activas.",
						"Multiple session", JOptionPane.INFORMATION_MESSAGE);
				password.setText("");
				username.setText("");
				break;
			case 4: // usuario con el mismo username REGISTRO
				JOptionPane.showMessageDialog(null, "Su nombre de usuario ya esta en uso.", "Registro. Username en uso",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case 5: // usuario con mismo alias REGISTRO
				JOptionPane.showMessageDialog(null, "Ya hay alguien registrado con este alias.",
						"Registro. Alias en uso", JOptionPane.INFORMATION_MESSAGE);
				break;
			case 6: // Usuario se conecta efectivamente despues de login. si se registra, 6, entra
					// directo a la pantalla
				// mandar a otra ventana, jpanel
				ArrayList<String> nombreGrupos = new ArrayList<String>();
				nombreGrupos = (ArrayList<String>) socketCliente.recibirObjeto();
				aliasUser = socketCliente.recibir();
				frame.setContentPane(
						new InicioCliente(frame, socketCliente, username.getText(), aliasUser, nombreGrupos, ab));
				frame.validate();
				break;
			default: // falta que pasa si el usuario se registra correctamente!!! numero y todo
				System.out.println("No se pudo");
				break;
			}
			/*
			 * if (verificacion) {
			 * 
			 * // HiloRecibir hiloRecibir = new HiloRecibir(socketCliente, textArea, //
			 * btnConectar, // btnDesconectar, textDesconectado, textNick, puerto, ip); //
			 * Thread thread = new Thread(hiloRecibir); // thread.start(); } else {
			 * SwingUtilities.invokeLater(new Runnable() {
			 * 
			 * @Override public void run() { JOptionPane.showMessageDialog(null,
			 * "Ya existe un usuario con ese nombre", "User invalid ~ CLIENTE",
			 * JOptionPane.INFORMATION_MESSAGE);
			 * 
			 * } }); }
			 */

		} catch (IllegalArgumentException e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, "Debe ingresar un número de puerto valido",
							"Invalid port ~ CLIENTE", JOptionPane.INFORMATION_MESSAGE);
				}
			});

		} catch (NoRouteToHostException e) {
			JOptionPane.showMessageDialog(null, "Mal ingreso de IP. Por favor, reingrese los valores",
					"ERROR IP ~ CLIENTE", JOptionPane.INFORMATION_MESSAGE);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
				}
			});

		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Mal ingreso de IP o puerto. Tambien, el servidor puede no estar activo.",
					"ERROR CONNECTION ~ CLIENTE", JOptionPane.INFORMATION_MESSAGE);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
				}
			});

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Mal ingreso de IP. Por favor, reingrese los valores",
					"ERROR IP ~ CLIENTE", JOptionPane.INFORMATION_MESSAGE);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
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
