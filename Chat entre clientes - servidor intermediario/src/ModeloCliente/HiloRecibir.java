package ModeloCliente;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.sun.org.apache.bcel.internal.generic.CASTORE;

import Binario.ArchivoBinario;
import modeloMensajes.MensajeChat;
import Sockets.progredes.SocketClienteTCP;
import interfazCliente.InicioCliente;
import interfazCliente.MainCliente;

public class HiloRecibir implements Runnable {

	private SocketClienteTCP socketCliente;
	private JTextArea textArea;
	private MensajeChat mensajeChat;
	private JLabel nombreGrupo;
	private JFrame frame;
	private ArchivoBinario ab1;
	private String username;
	private String alias;
	private ArrayList<String> nombreGruposUsuario;
	
	public HiloRecibir(SocketClienteTCP _socketCliente, JTextArea _textArea, JLabel _nombreGrupo, JFrame _frame, ArchivoBinario _ab1, String username,
			String alias, ArrayList<String> nombreGruposUsuario) {
		this.socketCliente = _socketCliente;
		this.textArea = _textArea;
		this.nombreGrupo = _nombreGrupo;
		frame = _frame;
		ab1 = _ab1;
		this.username = username;
		this.alias = alias;
		this.nombreGruposUsuario = nombreGruposUsuario;
	}

	@Override
	public void run() {
		try {
			while (true) {
				mensajeChat = (MensajeChat) socketCliente.recibirObjeto();
				if(mensajeChat == null) {
					throw new Exception();
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (textArea.getText().isEmpty()) {
							textArea.setText(mensajeChat.toString() + " " + mensajeChat.getEmisor() + ": " + mensajeChat.getMsj());
						} else {
							textArea.setText(textArea.getText() + "\n" + mensajeChat.toString() + " "
										+ mensajeChat.getEmisor() + ": " + mensajeChat.getMsj());
							textArea.setVisible(true);
						}
					}
				});
			}

		} catch (EOFException e) {
			JOptionPane.showMessageDialog(null, "Se desconectó el servidor", "Servidor desconectado  ~ CLIENTE",
				JOptionPane.INFORMATION_MESSAGE);
			frame.setContentPane(new MainCliente(frame, socketCliente, ab1));
			frame.revalidate();
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					nombreGrupo.setText("");
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
				frame.setContentPane(new MainCliente(frame, socketCliente, ab1));
				frame.revalidate();
			} else {
				JOptionPane.showMessageDialog(null, "Te has desconectado correctamente", "Desconexión  ~ CLIENTE",
						JOptionPane.INFORMATION_MESSAGE);
			}
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					nombreGrupo.setText("");
				}
			});
			try {
				socketCliente.cerrar();
			} catch (NullPointerException e1) {
			} catch (EOFException e1) {
			} catch (IOException e1) {
			}
		}catch(ClassCastException e) {
			//borrar grupo
			JOptionPane.showMessageDialog(null, "Se borró el grupo", "Grupo eliminado  ~ CLIENTE",
					JOptionPane.INFORMATION_MESSAGE);
			nombreGruposUsuario.remove(nombreGrupo.getText());
			frame.setContentPane(new InicioCliente(frame, socketCliente, username, alias, nombreGruposUsuario, ab1));
			frame.revalidate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			
		} finally {
			MensajeChat mensajeNuevo = new MensajeChat();
			mensajeNuevo.setFechayhora(LocalDateTime.now());
			textArea.setText(textArea.getText() + "\n" + mensajeNuevo.toString() + " : "
					+ "~  Se ha terminado la conexion al chat  ~");
		}
	}
}
