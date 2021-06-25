package ModeloServidor;

import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import Modelo.Mensaje;
import Modelo.Usuario;
import Sockets.progredes.SocketClienteTCP;
import Sockets.progredes.SocketServidorTCP;

public class hiloAceptarClientes implements Runnable {
	private SocketClienteTCP cliente;
	private SocketServidorTCP socket;
	private DefaultTableModel model;
	private String nombre;
	private ArrayList<Usuario> usuarios;
	private Usuario user;
	private Boolean verificar;

	public hiloAceptarClientes(SocketServidorTCP _socket, DefaultTableModel _model, ArrayList<Usuario> _usuarios) {
		this.socket = _socket;
		this.model = _model;
		this.usuarios = _usuarios;
	}

	@Override
	public void run() {

		while (true) {
			try {
				verificar = true;

				socket.aceptar();
				nombre = socket.recibir();

				for (Usuario usuario : usuarios) {
					if (usuario.getNombreUsuario().equals(nombre)) {
						socket.enviar(false);
						try {
							socket.getSocket().close();
						} catch (NullPointerException e) {
						} catch (SocketException e) {
						} catch (IOException e) {
						}
						verificar = false;
					}
				}

				if (verificar) {
					socket.enviar(true);

					cliente = new SocketClienteTCP();
					cliente.setSocket(socket.getSocket());

					user = new Usuario(nombre, cliente);
					Mensaje msj = new Mensaje("~  Se ha conectado el usuario " + user.getNombreUsuario() + "  ~",
							LocalDateTime.now(), "");
					for (Usuario s : usuarios) {
						try {
							s.getSocket().enviarObjeto(msj);
						} catch (IOException e1) {
						}
					}
					usuarios.add(user);

					new Thread(new hiloRecibir(user, usuarios, model)).start();
					new Thread(new hiloTablaClientes(model, usuarios)).start();
				}

			} catch (SocketException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
