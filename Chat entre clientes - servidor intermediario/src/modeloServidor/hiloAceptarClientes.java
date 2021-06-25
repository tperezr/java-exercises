package modeloServidor;

import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import modeloMensajes.Mensaje;
import modeloMensajes.MensajeLogin;
import modeloMensajes.MensajeRegistro;
import modelo.GrupoDeUsuarios;
import modelo.Usuario;
import Sockets.progredes.SocketClienteTCP;
import Sockets.progredes.SocketServidorTCP;
import dao.GrupoDAO;
import dao.UsuarioDAO;
import Texto.ArchivoTexto;;

public class hiloAceptarClientes implements Runnable {
	private ArchivoTexto txt;
	private SocketClienteTCP cliente;
	private SocketServidorTCP socket;
	private UsuarioDAO uDao = new UsuarioDAO();
	private GrupoDAO gDao = new GrupoDAO();

	private String nombre = "";
	private String contraseña = "";
	private String alias;
	private Mensaje mensaje;

	private ArrayList<Usuario> usuariosConectados;
	private ArrayList<GrupoDeUsuarios> grupos;
	private Usuario user;
	private Boolean verificar;
	private DefaultTableModel dataModel;

	public hiloAceptarClientes(SocketServidorTCP _socket, ArrayList<Usuario> _usuariosConectados,
			ArrayList<GrupoDeUsuarios> _grupos, ArchivoTexto _txt, DefaultTableModel _dataModel) {
		this.socket = _socket;
		this.usuariosConectados = _usuariosConectados;
		this.grupos = _grupos;
		this.txt = _txt;
		this.dataModel = _dataModel;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {

		try {
			while (true) {
				verificar = true;

				socket.aceptar();
				mensaje = (Mensaje) socket.recibirObjeto();
				ArrayList<Usuario> usuarios = uDao.seleccionarTodosUsuarios();
				if (mensaje.getCodigo() == 1) {
					// LogIn
					try {
						MensajeLogin login = (MensajeLogin) mensaje.getObjecto();
						nombre = login.getUsername();
						contraseña = login.getPassword();
					} catch (Exception e) {
						e.printStackTrace();
					}

					user = uDao.usuarioPorNombre(nombre);
					if (user == null) {
						throw new NullPointerException();
					}

					for (Usuario u : usuariosConectados) {
						// chequeo credenciales
						if (u.getNombreUsuario().equals(nombre)) {
							socket.enviar("3"); // usuario conectado - 3
							try {
								socket.getSocket().close();
							} catch (IOException e) {
							}
							verificar = false;
							break;
						}
					}

					if (user.getContraseña().equals(contraseña) && verificar) {
						conectar();
					} else {
						socket.enviar("2"); // pass erronea - 2
						try {
							socket.getSocket().close();
						} catch (IOException e) {
						}
					}
				} else {
					// registro
					nombre = ((MensajeRegistro) mensaje.getObjecto()).getUsername();
					contraseña = ((MensajeRegistro) mensaje.getObjecto()).getPassword();
					alias = ((MensajeRegistro) mensaje.getObjecto()).getAlias();

					for (Usuario u : usuarios) {
						if (!u.getNombreUsuario().equals(nombre)) {
							if (!u.getAlias().equals(alias)) {
							} else {
								verificar = false;
								socket.enviar("5"); // ya existe un usuario con el mismo alias - 5
								try {
									socket.getSocket().close();
								} catch (IOException e) {
								}
								break;
							}
						} else {
							verificar = false;
							socket.enviar("4"); // ya existe un usuario con el mismo nombre - 4
							try {
								socket.getSocket().close();
							} catch (IOException e) {
							}
							break;
						}

					}

					if (verificar) {
						uDao.registrarUsuario(nombre, alias, contraseña);
						user = uDao.usuarioPorNombre(nombre);
						conectar();
					}
				}
			}
		} catch (SocketException e) {

		} catch (NullPointerException e) {
			/**
			 * Tenemos que ponernos de acuerdo con los codigos de inicio de sesion 1-
			 * usuario no encontrado 2- pass erronea 3- usuario conectado 4- ya existe un
			 * usuario con el mismo nombre 5- ya existe un usuario con el mismo alias 6- se
			 * conectó
			 */
			try {
				socket.enviar("1");// no existe 1
				socket.getSocket().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void conectar() throws IOException {

		socket.enviar("6"); // se conecto - 6
		ArrayList<String> gruposDelUsuario = gDao.gruposPorUsuario(user.getId());
		socket.enviarObjeto(gruposDelUsuario);
		socket.enviar(user.getAlias());

		cliente = new SocketClienteTCP();
		cliente.setSocket(socket.getSocket());

		user.setSocket(cliente);
		usuariosConectados.add(user);

		txt.archivoParaEscribir(true);
		txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')
				+ " [Server info]: Se ha conectado el usuario " + user.getNombreUsuario());

		new Thread(new hiloRecibir(user, usuariosConectados, txt, grupos, dataModel)).start();

	}
}
