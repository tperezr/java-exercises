package ModeloServidor;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.Mensaje;
import Modelo.Usuario;

public class hiloRecibir implements Runnable {
	private ArrayList<Usuario> usuarios;
	private Mensaje mensaje;
	private DefaultTableModel model;
	private Usuario user;

	public hiloRecibir(Usuario _user, ArrayList<Usuario> _usuarios, DefaultTableModel _model) {
		this.user = _user;
		this.usuarios = _usuarios;
		this.model = _model;
	}

	@Override
	public void run() {
		try {
			
			while (user.getSocket().socketIsConnected()) {
				mensaje = (Mensaje) user.getSocket().recibirObjeto();
				for (Usuario s : usuarios) {
					if (s.getSocket() != user.getSocket()) {
						s.getSocket().enviarObjeto(mensaje);
					}
				}
			}
			
		} catch (SocketException e) {
			
			if(e.getMessage() == "Connection reset") {
				Mensaje msj = new Mensaje("~  Se ha desconectado el usuario " + user.getNombreUsuario() + "  ~", LocalDateTime.now(),"");
				usuarios.remove(user);
				for (Usuario s : usuarios) {
					if (s.getSocket() != user.getSocket()) {
						try {
							s.getSocket().enviarObjeto(msj);
						} catch (IOException e1) {
						}
					}
				}
				JOptionPane.showMessageDialog(null, "~  Se ha desconectado el cliente " + user.getNombreUsuario() + "  ~", "User disconnected ~ SERVER",
						JOptionPane.INFORMATION_MESSAGE);
			}
			new Thread(new hiloTablaClientes(model, usuarios)).start();
			
		} catch (EOFException e) {
			Mensaje msj = new Mensaje("~  Se ha desconectado el usuario " + user.getNombreUsuario() + "  ~", LocalDateTime.now(),"");
			usuarios.remove(user);
			for (Usuario s : usuarios) {
				if (s.getSocket() != user.getSocket()) {
					try {
						s.getSocket().enviarObjeto(msj);
					} catch (IOException e1) {
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Se ha desconectado el cliente " + user.getNombreUsuario(), "User disconnected ~ SERVER",
					JOptionPane.INFORMATION_MESSAGE);
			
			new Thread(new hiloTablaClientes(model, usuarios)).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
